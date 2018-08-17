package com.ruangwit.tor.appstructrue.core


import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import com.hwangjr.rxbus.RxBus
import com.ruangwit.tor.common.view.activity.BaseActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class AppStructureActivity : BaseActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    fun <VM : ViewModel> bindViewModel(cls: Class<VM>): VM {
        return ViewModelProviders.of(this, viewModelFactory).get(cls)
    }

    fun <VM : ViewModel> bindViewModel(key: String, cls: Class<VM>): VM {
        return ViewModelProviders.of(this, viewModelFactory).get(key, cls)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxBus.get().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.get().unregister(this)
    }


}