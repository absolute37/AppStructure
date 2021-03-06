package com.ruangwit.tor.common.view.fragment

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ruangwit.tor.common.exception.NotSetLayoutException
import timber.log.Timber

abstract class BaseFragment : Fragment(){
    lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let { bundle ->
            Timber.v("restoreInstanceState: bundle=$bundle")
            restoreInstanceState(bundle)
        } ?: run {
            Timber.v("restoreArgument: bundle=$arguments")
            restoreArgument(arguments)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.v("onCreateView")
        Timber.v("setupLayoutView")
        val layoutResourceId = setupLayoutView()
        if (setupLayoutView() == 0) throw NotSetLayoutException()
        return inflater.inflate(layoutResourceId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.v("onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        rootView = view
        Timber.v("bindView")
        bindView(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Timber.v("onActivityCreated")
        super.onActivityCreated(savedInstanceState)
        Timber.v("prepare")
        prepare()
        savedInstanceState?.let {
            Timber.v("prepare")
            restore()
        } ?: run {
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
        Timber.v("onPause")
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

    override fun onSaveInstanceState(outState: Bundle) {
        Timber.v("onSaveInstanceState")
        super.onSaveInstanceState(outState)
        Timber.v("saveInstanceState")
        saveInstanceState(outState)
    }

    @LayoutRes
    abstract fun setupLayoutView(): Int

    abstract fun bindView(view: View)

    abstract fun prepare()

    abstract fun restoreArgument(bundle: Bundle?)

    abstract fun initialize()

    abstract fun restoreInstanceState(savedInstanceState: Bundle?)

    abstract fun restore()

    abstract fun setup()

    abstract fun saveInstanceState(outState: Bundle?)

}