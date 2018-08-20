package com.ruangwit.tor.appstructrue.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.ruangwit.tor.appstructrue.api.ApiResponse
import com.ruangwit.tor.appstructrue.api.ApiResponse.ApiErrorResponse
import com.ruangwit.tor.appstructrue.api.ApiResponse.ApiEmptyResponse
import com.ruangwit.tor.appstructrue.api.ApiResponse.ApiSuccessResponse
import com.ruangwit.tor.appstructrue.vo.core.Resource
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response
import javax.xml.transform.Result

abstract class DirectNetworkBoundResource<ResultType, RequestType> @MainThread constructor(private val appExecutors: AppExecutors) {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        fetchFromNetwork()

    }

    @MainThread
    fun setValue(newValue: Resource<ResultType>) {
        if (result.value != null) {
            result.value = newValue
        }

    }

    private fun fetchFromNetwork() {
        val deferred: Deferred<Response<RequestType>> = createCall()
        val apiResponse = RepositoryLiveData(deferred)
        apiResponse.execute()
        setValue(Resource.loading(null))
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        val data: ResultType = convertToResultType(processResponse(response))
                        appExecutors.mainThread().execute {
                            setValue(Resource.success(data))
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        setValue(Resource.success(null))
                    }
                }
                is ApiErrorResponse -> {
                    appExecutors.diskIO().execute {
                        callFailed(response.errorMessage)
                        appExecutors.mainThread().execute {
                            setValue(Resource.error(response.errorMessage, null))
                        }
                    }
                }
            }
        }
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @MainThread
    protected abstract fun createCall(): Deferred<Response<RequestType>>

    @WorkerThread
    protected abstract fun convertToResultType(response: RequestType): ResultType

    @WorkerThread
    protected abstract fun callFailed(errorMessage: String)
}