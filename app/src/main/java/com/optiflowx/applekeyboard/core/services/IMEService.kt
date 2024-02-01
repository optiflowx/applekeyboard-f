package com.optiflowx.applekeyboard.core.services

import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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
import com.optiflowx.applekeyboard.core.enums.KeyboardType
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import splitties.experimental.ExperimentalSplittiesApi
import splitties.views.InputType

@Suppress("DEPRECATION")
class IMEService : LifecycleInputMethodService(), ViewModelStoreOwner, SavedStateRegistryOwner {

    /**
     * This is the main entry point of the IME. This is called every time the user starts inputting
     * and the IME is asked to provide a view.
     *
     * @param editorInfo Information about the text editor requesting the input, which we can use
     * to tailor the UI to the text field.
     * @param restarting Whether this is a restart of a previously running session.
     **/


    @OptIn(ExperimentalSplittiesApi::class)
    override fun onStartInputView(editorInfo: EditorInfo?, restarting: Boolean) {
        //Get the input type of the editor
        val vm = KeyboardViewModel(this)
        val action = editorInfo?.imeOptions?.and(EditorInfo.IME_MASK_ACTION)
        val noEnterAction = editorInfo?.imeOptions?.and(EditorInfo.IME_FLAG_NO_ENTER_ACTION)


        when (editorInfo?.inputType?.and(EditorInfo.IME_MASK_ACTION)) {
            InputType.number.value -> vm.onUpdateKeyboardType(KeyboardType.Number)
            InputType.phone.value -> vm.onUpdateKeyboardType(KeyboardType.Phone)

            else -> {
                val newType = when (vm.keyboardType.value) {
                    KeyboardType.Emoji, KeyboardType.Symbol, KeyboardType.Clipboard -> vm.keyboardType.value
                    else -> KeyboardType.Normal
                }
                vm.onUpdateKeyboardType(newType)
//                vm.keyboardType.value = when (keyboardType.value) {
//                    KeyboardType.Emoji, KeyboardType.Symbol, KeyboardType.Clipboard -> keyboardType.value
//                    else -> KeyboardType.Normal
//                }

            }
        }

        super.onStartInputView(editorInfo, restarting)
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        Log.d("IMEService", "onFinishInputView: $finishingInput")
        super.onFinishInputView(finishingInput)
    }

    override fun onDestroy() {
        store.clear()
        super.onDestroy()
    }


    override fun onCreateInputView(): View {
        val view = AppleKeyboardView(this)

        window?.window?.decorView?.let { decorView ->
            decorView.setViewTreeLifecycleOwner(this)
            decorView.setViewTreeViewModelStoreOwner(this)
            decorView.setViewTreeSavedStateRegistryOwner(this)
        }

        val windowCompat = window?.window

        if (windowCompat != null) {

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

//            val radius = 20f
//
//            // ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
////            val rootView = windowCompat.decorView.findViewById<ViewGroup>(android.R.id.content)
//             val blurView = view.findViewById<BlurView>(R.id.keyboard_blur_view)
//
//            val decorView = this.window.window?.decorView
//            val rootView = view.rootView as ViewGroup
//            val windowBackground = decorView?.background
//
//
//            blurView.setupWith(rootView, RenderScriptBlur(this)) // or RenderEffectBlur
//                .setFrameClearDrawable(windowBackground)
//                .setBlurRadius(radius)
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
