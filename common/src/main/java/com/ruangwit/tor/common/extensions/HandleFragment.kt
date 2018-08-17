package com.ruangwit.tor.common.extensions

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager


fun Activity.addFragment(supportFragmentManager: FragmentManager, layout: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().add(layout, fragment).commit()
}

fun Activity.replaceFragment(supportFragmentManager: FragmentManager, layout: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(layout, fragment).commit()
}

fun Activity.removeFragment(supportFragmentManager: FragmentManager, fragment: Fragment) {
    supportFragmentManager.beginTransaction().remove(fragment).commit()
}