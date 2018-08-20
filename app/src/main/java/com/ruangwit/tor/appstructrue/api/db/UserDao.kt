package com.ruangwit.tor.appstructrue.api.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.ruangwit.tor.appstructrue.vo.User

@Dao
abstract class UserDao {

    @Query("SELECT * FROM user ")
    abstract fun getUser(): LiveData<User>

}