package com.ruangwit.tor.appstructrue.repo.room

import android.app.Application
import android.arch.persistence.room.Room
import com.ruangwit.tor.appstructrue.api.db.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(app: Application): DatabaseManager {
        return Room.databaseBuilder(app, DatabaseManager::class.java, "appstruceture.db").fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: DatabaseManager): UserDao {
        return db.userDao()
    }
}