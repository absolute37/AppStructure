package com.ruangwit.tor.common.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment

inline fun <reified T> Activity.openActivity(cls: Class<T>) {
    startActivity(Intent(this, cls))
}

inline fun <reified T> Activity.openActivity(cls: Class<T>, bundle: Bundle) {
    startActivity(Intent(this, cls).apply {
        putExtras(bundle)
    })
}

inline fun <reified T> Activity.openActivityAndClearThisActivity(cls: Class<T>) {
    startActivity(Intent(this, cls))
    finish()
}

inline fun <reified T> Activity.openActivityAndClearThisActivity(cls: Class<T>, bundle: Bundle) {
    startActivity(Intent(this, cls).apply {
        putExtras(bundle)
    })
    finish()
}

inline fun <reified T> Activity.openActivityAndClearAllActivity(cls: Class<T>) {
    startActivity(Intent(this, cls).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    })
    finish()
}

inline fun <reified T> Activity.openActivityAndClearAllActivity(cls: Class<T>, bundle: Bundle) {
    startActivity(Intent(this, cls).apply {
        putExtras(bundle)
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    })
    finish()
}

inline fun <reified T> Fragment.openActivity(cls: Class<T>) {
    startActivity(android.content.Intent(activity, cls))
}

inline fun <reified T> Fragment.openActivity(cls: Class<T>, bundle: Bundle) {
    startActivity(android.content.Intent(activity, cls).apply {
        putExtras(bundle)
    })
}

inline fun <reified T> Fragment.openActivityAndClearThisActivity(cls: Class<T>) {
    startActivity(android.content.Intent(activity, cls))
    activity?.finish()
}

inline fun <reified T> Fragment.openActivityAndClearThisActivity(cls: Class<T>, bundle: Bundle) {
    startActivity(android.content.Intent(activity, cls).apply {
        putExtras(bundle)
    })
    activity?.finish()
}

inline fun <reified T> Fragment.openActivityAndClearAllActivity(cls: Class<T>) {
    startActivity(android.content.Intent(activity, cls).apply {
        addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP)

    })
    activity?.finish()
}

inline fun <reified T> Fragment.openActivityAndClearAllActivity(cls: Class<T>, bundle: Bundle) {
    startActivity(android.content.Intent(activity, cls).apply {
        putExtras(bundle)
        addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP)
    })
    activity?.finish()
}