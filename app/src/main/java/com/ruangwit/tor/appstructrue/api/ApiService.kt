package com.ruangwit.tor.appstructrue.api

import android.arch.lifecycle.LiveData
import com.ruangwit.tor.appstructrue.vo.User
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("user")
    fun getUser(): LiveData<ApiResponse<User>>

}