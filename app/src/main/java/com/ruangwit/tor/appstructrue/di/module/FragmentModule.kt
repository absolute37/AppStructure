package com.ruangwit.tor.appstructrue.di.module

import com.ruangwit.tor.appstructrue.ui.fragment.view.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeAppointmentFragment(): LoginFragment

}