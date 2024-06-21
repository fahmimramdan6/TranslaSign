package com.dicoding.translasign.data.api

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("expirationTime")
    val expirationTime: Long? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("token")
    val token: String? = null,
)
