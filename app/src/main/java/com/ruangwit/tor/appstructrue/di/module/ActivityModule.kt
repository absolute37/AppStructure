package com.ruangwit.tor.appstructrue.di.module

import com.ruangwit.tor.appstructrue.ui.acvitity.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeHomeActivity(): MainActivity

}