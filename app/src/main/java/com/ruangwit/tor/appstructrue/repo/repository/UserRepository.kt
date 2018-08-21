package com.ruangwit.tor.appstructrue.repo.repository

import android.arch.lifecycle.LiveData
import com.ruangwit.tor.appstructrue.api.ApiService
import com.ruangwit.tor.appstructrue.api.db.UserDao
import com.ruangwit.tor.appstructrue.repo.AppExecutors
import com.ruangwit.tor.appstructrue.repo.NetworkBoundResource
import com.ruangwit.tor.appstructrue.vo.User
import com.ruangwit.tor.appstructrue.vo.core.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val appExecutors: AppExecutors,
                                         private val userDao: UserDao,
                                         private val apiService: ApiService) {


    fun loadUser(login: String): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>(appExecutors) {
            override fun saveCallResult(item: User) {
                userDao.insert(item)
            }

            override fun shouldFetch(data: User?): Boolean = data == null

            override fun loadFromDb() = userDao.findByLogin(login)

            override fun createCall() = apiService.getUser()

        }.asLiveData()

    }

}