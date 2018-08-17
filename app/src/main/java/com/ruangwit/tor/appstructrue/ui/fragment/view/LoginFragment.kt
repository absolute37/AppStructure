package com.ruangwit.tor.appstructrue.ui.fragment.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.ruangwit.tor.appstructrue.R
import com.ruangwit.tor.appstructrue.core.AppStructureFragment
import com.ruangwit.tor.appstructrue.ui.fragment.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_activity.edt_username as edtUserName
import kotlinx.android.synthetic.main.login_activity.edt_password as edtPassword
import kotlinx.android.synthetic.main.login_activity.btn_login as btnLogin
import javax.inject.Inject

class LoginFragment @Inject constructor() : AppStructureFragment() {
    lateinit var loginViewModel: LoginViewModel

    companion object {
        fun newInstance(): Fragment = LoginFragment()
    }

    override fun setupLayoutView(): Int {
        return R.layout.login_activity
    }

    override fun bindView(view: View) {

    }

    override fun prepare() {

    }

    override fun restoreArgument(bundle: Bundle?) {

    }

    override fun initialize() {

    }

    override fun restoreInstanceState(savedInstanceState: Bundle?) {

    }

    override fun restore() {

    }

    override fun setup() {
        loginViewModel = bindViewModel(LoginViewModel::class.java)


    }


    override fun saveInstanceState(outState: Bundle?) {

    }


}