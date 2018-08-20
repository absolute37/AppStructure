package com.ruangwit.tor.appstructrue.repo.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.ruangwit.tor.appstructrue.api.db.UserDao
import com.ruangwit.tor.appstructrue.vo.User
import java.text.StringCharacterIterator


@Database(entities = [User::class],
        version = 3)

@TypeConverters(StringCharacterIterator::class)
abstract class DatabaseManager : RoomDatabase() {

    abstract fun userDao(): UserDao


}