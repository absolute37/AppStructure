package com.ruangwit.tor.appstructrue.util

import android.arch.persistence.room.TypeConverter
import com.ruangwit.tor.common.extensions.fromJson
import com.ruangwit.tor.common.extensions.json

class StringTypeConverters {

    @TypeConverter
    fun fromListString(json: String?): List<String> = if (json != null && json.isNotEmpty()) json.fromJson() else listOf()

    @TypeConverter
    fun toString(stringList: List<String>?): String = stringList?.json() ?: "[]"


}