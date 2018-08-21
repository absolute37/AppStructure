package com.ruangwit.tor.appstructrue.api.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.ruangwit.tor.appstructrue.vo.User

@Dao
abstract class UserDao {

    @Query("SELECT * FROM user ")
    abstract fun getUser(): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(user: User)

    @Query("SELECT * FROM user WHERE login = :login")
    abstract fun findByLogin(login: String): LiveData<User>

}