package com.ruangwit.tor.appstructrue.di.module

import android.app.Application
import android.arch.persistence.room.Room
import com.ruangwit.tor.appstructrue.api.ApiService
import com.ruangwit.tor.appstructrue.api.LiveDataCallAdapterFactory
import com.ruangwit.tor.appstructrue.api.db.UserDao
import com.ruangwit.tor.appstructrue.repo.room.DatabaseManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(ApiService::class.java)
    }

}