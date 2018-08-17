package com.ruangwit.tor.appstructrue.core

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.hwangjr.rxbus.RxBus
import com.ruangwit.tor.appstructrue.di.Injectable
import com.ruangwit.tor.common.view.fragment.BaseFragment
import javax.inject.Inject

abstract class AppStructureFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    fun <VM : ViewModel> bindViewModel(cls: Class<VM>): VM =
            ViewModelProviders.of(this, viewModelFactory).get(cls)

    fun <VM : ViewModel> bindViewModel(key: String, cls: Class<VM>): VM =
            ViewModelProviders.of(this, viewModelFactory).get(key, cls)

    fun <VM : ViewModel> bindViewModelWithActivity(activity: FragmentActivity, cls: Class<VM>): VM =
            ViewModelProviders.of(activity, viewModelFactory).get(cls)

    fun <VM : ViewModel> bindViewModelWithActivity(activity: FragmentActivity, key: String, cls: Class<VM>): VM =
            ViewModelProviders.of(activity, viewModelFactory).get(key, cls)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxBus.get().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            RxBus.get().unregister(this)
        } catch (ignored: IllegalArgumentException) {
        }
    }
}