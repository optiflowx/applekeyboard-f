package com.optiflowx.optikeysx.services

import android.content.Intent
import android.inputmethodservice.InputMethodService
import android.view.inputmethod.EditorInfo
import androidx.annotation.CallSuper
import androidx.compose.runtime.Stable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ServiceLifecycleDispatcher

@Stable
abstract class LifecycleInputMethodService : InputMethodService(), LifecycleOwner {

    protected val dispatcher = ServiceLifecycleDispatcher(this)

    @CallSuper
    override fun onStartInputView(editorInfo: EditorInfo?, restarting: Boolean) {
        dispatcher.onServicePreSuperOnStart()
        super.onStartInputView(editorInfo, restarting)
    }

    @CallSuper
    override fun onCreate() {
        dispatcher.onServicePreSuperOnCreate()
        super.onCreate()
    }

    @CallSuper
    override fun onBindInput() {
        dispatcher.onServicePreSuperOnBind()
        super.onBindInput()
    }


    // this method is added only to annotate it with @CallSuper.
    // In usual service super.onStartCommand is no-op, but in LifecycleService
    // it results in mDispatcher.onServicePreSuperOnStart() call, because
    // super.onStartCommand calls onStart().
    @CallSuper
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    @CallSuper
    override fun onDestroy() {
        dispatcher.onServicePreSuperOnDestroy()
        super.onDestroy()
    }

}