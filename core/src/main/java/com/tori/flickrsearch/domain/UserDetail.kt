package com.tori.flickrsearch.domain

import com.google.gson.annotations.SerializedName

data class UserDetail(
    @SerializedName("_content") val content: String?
)