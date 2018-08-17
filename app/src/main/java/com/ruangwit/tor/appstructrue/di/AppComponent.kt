package com.ruangwit.tor.appstructrue.di

import android.app.Application
import android.content.Context
import com.ruangwit.tor.appstructrue.di.module.ActivityModule
import com.ruangwit.tor.appstructrue.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent

    }

    fun inject(mainApplication: MainApplication)

}