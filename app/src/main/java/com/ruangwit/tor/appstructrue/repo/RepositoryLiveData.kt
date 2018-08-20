package com.ruangwit.tor.appstructrue.repo

import android.arch.lifecycle.LiveData
import com.ruangwit.tor.appstructrue.api.ApiResponse
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import retrofit2.Response


class RepositoryLiveData<T>(var deferred: Deferred<Response<T>>) : LiveData<ApiResponse<T>>() {
    fun execute() {
        launch(UI) {
            try {
                val result: Response<T> = deferred.await()
                postValue(ApiResponse.create(result))
            } catch (e: Exception) {
                postValue(ApiResponse.create(e))

            }

        }
    }
}
