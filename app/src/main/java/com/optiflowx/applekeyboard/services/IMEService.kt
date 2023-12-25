package com.optiflowx.applekeyboard.services

import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.optiflowx.applekeyboard.AppleKeyboardView

@Suppress("DEPRECATION")
class IMEService : LifecycleInputMethodService(),
    ViewModelStoreOwner,
    SavedStateRegistryOwner {

//    Background blur
//
//    Use background blur on floating windows to create a window background effect which is a blurred image of the underlying content. To add a blurred background for your window, do the following:
//
//    Call Window#setBackgroundBlurRadius(int) to set a background blur radius. Or, in the window theme, set R.attr.windowBackgroundBlurRadius.
//    Set R.attr.windowIsTranslucent to true to make the window translucent. The blur is drawn under the window surface, so the window needs to be translucent to let the blur be visible.
//    Optionally, call Window#setBackgroundDrawableResource(int) to add a rectangular window background drawable with a translucent color. Or, in the window theme, set R.attr.windowBackground.
//    For a window with rounded corners, determine the rounded corners for the blurred area by setting a ShapeDrawable with rounded corners as the window background drawable.
//    Handle blur enabled and disabled states. Refer to the Guidelines to use window blur in apps section for more information.

    override fun onCreateInputView(): View {
        val view = AppleKeyboardView(this)

        window?.window?.decorView?.let { decorView ->
            decorView.setViewTreeLifecycleOwner(this)
            decorView.setViewTreeViewModelStoreOwner(this)
            decorView.setViewTreeSavedStateRegistryOwner(this)
        }

        val windowCompat = window?.window

        if (windowCompat != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                ResourcesCompat.getDrawable(resources, R.drawable.bg, null)?.let {
//                    windowCompat.setBackgroundBlurRadius(20)
//                    windowCompat.setBackgroundDrawable(it)
//                    windowCompat.setFlags(
//                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
//                    )
//                }
//            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                windowCompat.setDecorFitsSystemWindows(false)
            } else {
                ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
                    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                    insets.replaceSystemWindowInsets(
                        systemBars.left,
                        0,
                        systemBars.right,
                        0
                    )
                }
            }

            windowCompat.navigationBarColor = Color.Transparent.toArgb()

            val windowInsetsController =
                WindowCompat.getInsetsController(windowCompat, view)

            // Hide the system bars.
            windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
        }

        return view
    }


    override fun onCreate() {
        super.onCreate()
        savedStateRegistryController.performRestore(null)

    }

    override val viewModelStore: ViewModelStore
        get() = store
    override val lifecycle: Lifecycle
        get() = dispatcher.lifecycle


    //ViewModelStore Methods
    private val store = ViewModelStore()

    //SaveStateRegistry Methods

    private val savedStateRegistryController = SavedStateRegistryController.create(this)

    override val savedStateRegistry: SavedStateRegistry get() = savedStateRegistryController.savedStateRegistry
}
