package com.ruangwit.tor.appstructrue.vo

import android.arch.persistence.room.*

@Entity(tableName = "user")
data class User(@PrimaryKey
                @ColumnInfo(name = "id")
                val id: String?,
                @ColumnInfo(name = "name")
                val name: String?
)