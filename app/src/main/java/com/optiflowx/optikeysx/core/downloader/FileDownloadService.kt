package com.optiflowx.optikeysx.core.downloader

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.core.Tools
import com.optiflowx.optikeysx.core.downloader.messages.*
import com.optiflowx.optikeysx.extensions.DownloadsExtensions
import com.optiflowx.optikeysx.extensions.DownloadsExtensions.getTemporaryDownloadLocation
import com.optiflowx.optikeysx.extensions.DownloadsExtensions.getTemporaryUnzipLocation
import com.optiflowx.optikeysx.extensions.getNotificationPermission
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.*
import java.net.URL
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Suppress("DEPRECATION")
class FileDownloadService : Service() {
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private lateinit var notificationManager: NotificationManagerCompat
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private var currentModel: ModelInfo? = null
    private val queuedModels: Queue<ModelInfo> = LinkedList()
    private var currentState = State.NONE
    private var downloadProgress = 0f
    private var unzipProgress = 0f

    private var interrupt = false

    override fun onCreate() {
        super.onCreate()
        EventBus.getDefault().register(this)
        notificationManager = NotificationManagerCompat.from(this)
        notificationBuilder = NotificationCompat.Builder(this, DownloadsExtensions.DOWNLOADER_CHANNEL_ID)
        notificationBuilder.setContentTitle("OptiKeysX: Downloading a new model")
            .setContentText("Loading...")
            .setSmallIcon(R.drawable.icon_notification).priority = NotificationCompat.PRIORITY_LOW
        notificationBuilder.setProgress(0, 0, true)
        notificationBuilder.foregroundServiceBehavior =
            NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when (intent.getStringExtra(FileDownloader.ACTION)) {
            FileDownloader.ACTION_DOWNLOAD -> {
                val modelInfo = FileDownloader.getInfoForIntent(intent) ?: return START_NOT_STICKY
                Log.d(TAG, "Got message $modelInfo")
                queuedModels.add(modelInfo)
                sendEnqueued(modelInfo)
                executor.execute { main() }
            }

            FileDownloader.ACTION_UNZIP -> {
                val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(FileDownloader.UNZIP_URI, Uri::class.java)
                } else {
                    intent.getParcelableExtra(FileDownloader.UNZIP_URI)
                }
                executor.execute { unzipUri(uri!!) }
            }
        }
        startForeground(notificationId, notificationBuilder.build())

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            startForeground(notificationId, notificationBuilder.build())
        } else {
            startForeground(notificationId, notificationBuilder.build(),
                FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        }

        return START_NOT_STICKY
    }

    private fun unzipUri(uri: Uri) {
        Log.d(TAG, "Unzipping $uri")
        val filename = "ImportedFile.zip"
        val file = getTemporaryDownloadLocation(this, filename)
        Tools.copyStreamToFile(contentResolver!!.openInputStream(uri)!!, file)
        unzipProgress = 0f
        currentModel = ModelInfo(uri.toString(), file.name)
        setState(State.NONE)
        unzipFile(file)
        if (interrupt) {
            interrupted(file)
        }
        Log.d(TAG, "Finished unzipping")
        file.delete()
        setState(State.FINISHED)
        Log.d(TAG, "Finished processing $currentModel")
        mainEnd()
    }

    private fun main() {
        currentModel = queuedModels.poll()
        val currentModelS = currentModel ?: return
        Log.d(TAG, "Started processing $currentModelS")
        downloadProgress = 0f
        unzipProgress = 0f
        setState(State.NONE)
        val downloadLocation = getTemporaryDownloadLocation(
            applicationContext, currentModelS.filename
        )
        if (!downloadLocation.parentFile!!.exists()) {
            downloadLocation.parentFile!!.mkdirs()
        }
        try {
            downloadFile(downloadLocation)
        } catch (e: IOException) {
            e.printStackTrace()
            setError(e.message)
            mainEnd()
            return
        }
        if (interrupt) {
            interrupted(downloadLocation)
            return
        }
        Log.d(TAG, "Finished downloading")
        try {
            unzipFile(downloadLocation)
        } catch (e: IOException) {
            e.printStackTrace()
            setError(e.message)
            mainEnd()
            return
        }
        if (interrupt) {
            interrupted(downloadLocation)
        }
        Log.d(TAG, "Finished unzipping")
        downloadLocation.delete()
        setState(State.FINISHED)
        Log.d(TAG, "Finished processing $currentModel")
        mainEnd()
    }

    private fun interrupted(downloadLocation: File?) {
        downloadLocation?.let {
            if (it.exists()) {
                it.delete()
            }
        }
        val unzipFolder = getTemporaryUnzipLocation(this)
        if (unzipFolder.exists()) {
            Tools.deleteRecursive(unzipFolder)
        }

        Log.d(TAG, "Download Canceled")
        EventBus.getDefault().post(
            CancelFinished(currentModel!!)
        )
        updateNotification()

        interrupt = false
        mainEnd()
    }

    private fun mainEnd() {
        downloadProgress = 0f
        unzipProgress = 0f
        currentState = State.NONE
        currentModel = null
        if (queuedModels.isEmpty()) stopForeground(false)
    }

    @Throws(IOException::class)
    private fun downloadFile(downloadLocation: File) {
        setState(State.DOWNLOAD_STARTED)
        val url = URL(currentModel!!.url)
        val urlConnection = url.openConnection()
        urlConnection.connect()
        val lengthOfFile = urlConnection.contentLength
        Log.d("TAG", "Length of file: $lengthOfFile")
        setDownloadProgress(0f)
        val input: InputStream = BufferedInputStream(url.openStream())
        val output: OutputStream = FileOutputStream(downloadLocation)
        val data = ByteArray(1024) // 1mb
        var total: Float = 0f
        var count: Int
        while (input.read(data).also { count = it } != -1 && !interrupt) {
            total += count
            setDownloadProgress(total / lengthOfFile)
            output.write(data, 0, count)
        }
        output.flush()
        output.close()
        input.close()
        if (!interrupt) {
            setDownloadProgress(1f)
            setState(State.DOWNLOAD_FINISHED)
        }
    }

    @Throws(IOException::class)
    private fun unzipFile(downloadLocation: File) {
        setState(State.UNZIP_STARTED)
//        val unzipDestination = getDirectoryForModel(
//            applicationContext, currentModel!!.locale
//        )
//        if (!unzipDestination.exists()) {
//            unzipDestination.mkdirs()
//        }
//        val unzipFolder = getTemporaryUnzipLocation(this)
//        val currentUnzipFolder = File(unzipFolder, unzipDestination.name)
        ZipTools.unzip(
            downloadLocation, currentModel!!.locale, applicationContext,
            errorObserver = {
                setError(it)
            }
        ) { d: Double -> setUnzipProgress(d.toFloat()) }
        setUnzipProgress(1f)
        setState(State.UNZIP_FINISHED)
    }

    private var lastDownloadProgress = 0f
    private var lastUnzipProgress = 0f
    private var lastState: State = State.NONE
    private var lastUpdateTime: Long = 0
    private fun updateNotification() {
        if (lastDownloadProgress == downloadProgress && lastUnzipProgress == unzipProgress && lastState == currentState) // nothing changed
            return
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastUpdateTime < minUpdateTime) {
            if (lastState == currentState) { // it's a progress update
                if (!(downloadProgress == 1f && unzipProgress == 0f) && unzipProgress != 1f) { // it's not the last progress update
                    lastUpdateTime = currentTime
                    return
                }
            }
        }
        lastUpdateTime = currentTime
        when (currentState) {
            State.NONE -> notificationBuilder.setContentText("Unknown")
                .setProgress(0, 0, true)

            State.DOWNLOAD_STARTED, State.DOWNLOAD_FINISHED -> notificationBuilder.setContentText(
                "Download has started"
            ).setProgress(PROGRESS_MAX, (downloadProgress * PROGRESS_MAX).toInt(), false)

            State.UNZIP_STARTED, State.UNZIP_FINISHED -> notificationBuilder.setContentText(
                "Unzipping the model"
            ).setProgress(PROGRESS_MAX, (unzipProgress * PROGRESS_MAX).toInt(), false)

            State.FINISHED -> notificationBuilder.setContentText("Download Completed!")
                .setProgress(0, 0, false)

            State.ERROR -> notificationBuilder.setContentText("An error occurred while downloading.")
                .setProgress(0, 0, false)

            State.CANCELED -> notificationBuilder.setContentText("Downloading Canceled")
                .setProgress(0, 0, false)

            else -> {}
        }

        if (ActivityCompat.checkSelfPermission(
                this, getNotificationPermission()
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(notificationId, notificationBuilder.build())
        }
        lastDownloadProgress = downloadProgress
        lastUnzipProgress = unzipProgress
        lastState = currentState
    }

    private fun setState(state: State) {
        currentState = state
        EventBus.getDefault().post(DownloadState(currentModel!!, state))
        updateNotification()
    }

    private fun setDownloadProgress(progress: Float) {
        downloadProgress = progress
        EventBus.getDefault().post(DownloadProgress(currentModel!!, downloadProgress))
        updateNotification()
    }

    private fun setUnzipProgress(progress: Float) {
        unzipProgress = progress
        EventBus.getDefault().post(UnzipProgress(currentModel!!, unzipProgress))
        updateNotification()
    }

    private fun setError(message: String?) {
        setState(State.ERROR)
        EventBus.getDefault().post(DownloadError(currentModel!!, message ?: ""))
        updateNotification()
    }

    private fun sendEnqueued(modelInfo: ModelInfo) {
        EventBus.getDefault().post(DownloadState(modelInfo, State.QUEUED))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleStatusQuery() {
        EventBus.getDefault()
            .post(Status(currentModel, queuedModels, downloadProgress, unzipProgress, currentState))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleCancelPending(event: CancelPending) {
        if (queuedModels.remove(event.info)) {
            EventBus.getDefault().post(
                Status(
                    currentModel, queuedModels, downloadProgress, unzipProgress, currentState
                )
            )
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleCancelCurrent(event: CancelCurrent) {
        if (currentModel == event.info) {
            interrupt = true
            setState(State.CANCELED)
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
        EventBus.getDefault().unregister(this)
    }

    companion object {
        private const val TAG = "FileDownloadService"
        private const val notificationId = 1
        private const val PROGRESS_MAX = 100
        private const val minUpdateTime = (1000 / 5 // 5 updates a second;
                ).toLong()
    }
}