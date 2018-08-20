package com.ruangwit.tor.appstructrue.repo

import android.arch.lifecycle.MutableLiveData

class Trigger<T> {

    private val liveData: MutableLiveData<Data<T>> = MutableLiveData()

    fun pull(value: T, needFresh: Boolean) {
        liveData.value = Data(value, needFresh)
    }

    fun pull(value: T) {
        pull(value, false)
    }

    fun Freshpull(value: T) {
        pull(value, true)

    }

    fun getLiveData() = liveData

    data class Data<T>(var value: T, var cl: Boolean?)
}