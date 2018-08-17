package com.ruangwit.tor.common.extensions

import android.content.Context
import com.ruangwit.tor.common.R

val Context.isTablet: Boolean
    get() = this.resources.getBoolean(R.bool.is_tablet)

val Context.isPhone: Boolean
    get() = this.resources.getBoolean(R.bool.is_tablet)