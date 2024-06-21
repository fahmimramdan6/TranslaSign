package com.dicoding.translasign.data.api

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null,
)
