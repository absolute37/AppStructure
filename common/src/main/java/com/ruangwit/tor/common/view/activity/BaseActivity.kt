package com.ruangwit.tor.common.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import com.ruangwit.tor.common.exception.NotSetLayoutException
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            // Replace fragment here
        }
        Timber.v("setupLayoutView")
        val layoutResId = setupLayoutView()
        if (setupLayoutView() == 0) throw NotSetLayoutException()
        setContentView(layoutResId)
        Timber.v("bindView")
        bindView()
        Timber.v("prepare")
        prepare()
        savedInstanceState?.let { bundle ->
            Timber.v("restoreInstanceState: bundle=$bundle")
            restoreInstanceState(bundle)
            Timber.v("prepare")
            restore()
        } ?: run {
            Timber.v("restoreArgument: bundle=${intent.extras}")
            restoreArgument(intent.extras)
            Timber.v("initialize")
            initialize()
        }
        Timber.v("setup")
        setup()
    }

    override fun onStart() {
        Timber.v("onStart")
        super.onStart()
    }

    override fun onResume() {
        Timber.v("onResume")
        super.onResume()
    }

    override fun onPause() {
        Timber.v("onPause")
        super.onPause()
    }

    override fun onStop() {
        Timber.v("onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Timber.v("onDestroy")
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        Timber.v("onSaveInstanceState")
        super.onSaveInstanceState(outState)
        Timber.v("saveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        Timber.v("onRestoreInstanceState")
        super.onRestoreInstanceState(savedInstanceState)
    }

    @LayoutRes
    abstract fun setupLayoutView(): Int

    abstract fun bindView()

    abstract fun prepare()

    abstract fun restoreArgument(bundle: Bundle?)

    abstract fun initialize()

    abstract fun restoreInstanceState(savedInstanceState: Bundle?)

    abstract fun restore()

    abstract fun setup()

    abstract fun saveInstanceState(outState: Bundle?)


}