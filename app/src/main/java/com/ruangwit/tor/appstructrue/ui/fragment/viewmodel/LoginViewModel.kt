package com.ruangwit.tor.appstructrue.ui.fragment.viewmodel


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.ruangwit.tor.appstructrue.api.AbsentLiveData
import com.ruangwit.tor.appstructrue.repo.repository.UserRepository
import com.ruangwit.tor.appstructrue.vo.User
import com.ruangwit.tor.appstructrue.vo.core.Resource
import javax.inject.Inject

class LoginViewModel @Inject constructor(userRepository: UserRepository) : ViewModel() {

    private val _login = MutableLiveData<String>()

    val login: LiveData<String> get() = _login


    val user: LiveData<Resource<User>> = Transformations
            .switchMap(_login) { login ->
                if (login == null) {
                    AbsentLiveData.create()
                } else {
                    userRepository.loadUser(login)
                }
            }


    fun setLogin(login: String?) {
        if (_login.value != login) {
            _login.value = login
        }

    }

    fun retry() {
        _login.value?.let {
            _login.value = it
        }

    }


}