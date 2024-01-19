package com.optiflowx.applekeyboard.services

import android.os.Build
import android.view.View
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

@Suppress("DEPRECATION")
class IMEService : LifecycleInputMethodService(), ViewModelStoreOwner, SavedStateRegistryOwner {

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
