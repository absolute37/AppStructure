package com.ruangwit.tor.appstructrue.repo

import android.arch.lifecycle.LiveData

class SingleLiveData<T>(input: T) : LiveData<T>() {
    init {
        postValue(input)
    }

}