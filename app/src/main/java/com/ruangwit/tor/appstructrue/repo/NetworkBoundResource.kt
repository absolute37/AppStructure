package com.ruangwit.tor.appstructrue.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.ruangwit.tor.appstructrue.api.ApiResponse.ApiSuccessResponse
import com.ruangwit.tor.appstructrue.api.ApiResponse.ApiErrorResponse
import com.ruangwit.tor.appstructrue.api.ApiResponse.ApiEmptyResponse
import com.ruangwit.tor.appstructrue.vo.core.Resource
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData))
                }
            }

        }

    }

    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val deferred: Deferred<Response<RequestType>> = createCall()
        val apiResponse = RepositoryLiveData(deferred)
        apiResponse.execute()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))

        }

        result.addSource(apiResponse) { response ->
            result.removeSource(dbSource)
            result.removeSource(apiResponse)
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        val data: ResultType = convertToResultType(processResponse(response))
                        saveCallResult(data)
                        appExecutors.mainThread().execute {
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(newData))
                            }

                        }
                    }

                }
                is ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        // reload from disk whatever we had
                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }
                is ApiErrorResponse -> {
                    appExecutors.diskIO().execute {
                        callFailed(response.errorMessage)
                        appExecutors.mainThread().execute {
                            result.addSource(dbSource) { newData ->
                                setValue(Resource.error(response.errorMessage, newData))
                            }
                        }

                    }

                }

            }

        }

    }


    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun callFailed(errorMessage: String)


    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean


    @MainThread
    protected abstract fun createCall(): Deferred<Response<RequestType>>

    @WorkerThread
    protected abstract fun saveCallResult(item: ResultType)


    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract fun convertToResultType(response: RequestType): ResultType


}