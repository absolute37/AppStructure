package com.ruangwit.tor.appstructrue.vo

import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["login"])
data class User(
        @field:SerializedName("login")
        val login: String,
        @field:SerializedName("avatar_url")
        val avatarUrl: String?,
        @field:SerializedName("name")
        val name: String?,
        @field:SerializedName("company")
        val company: String?,
        @field:SerializedName("repos_url")
        val reposUrl: String?,
        @field:SerializedName("blog")
        val blog: String?
)