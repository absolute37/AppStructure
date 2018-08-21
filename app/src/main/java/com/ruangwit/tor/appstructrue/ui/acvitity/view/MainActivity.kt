package com.ruangwit.tor.appstructrue.ui.acvitity.view

import android.os.Bundle
import com.ruangwit.tor.appstructrue.R
import com.ruangwit.tor.appstructrue.core.AppStructureActivity
import com.ruangwit.tor.appstructrue.ui.acvitity.viewmodel.MainViewModel
import com.ruangwit.tor.appstructrue.ui.fragment.view.LoginFragment
import com.ruangwit.tor.common.extensions.addFragment
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppStructureActivity() {

    private lateinit var mainViewModel: MainViewModel


    override fun saveInstanceState(outState: Bundle?) {
    }

    override fun setupLayoutView(): Int {
        return R.layout.activity_main
    }

    override fun bindView() {
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
        mainViewModel = bindViewModel(MainViewModel::class.java)
        addFragment(supportFragmentManager, R.id.container_layout, LoginFragment.newInstance())

    }


}



