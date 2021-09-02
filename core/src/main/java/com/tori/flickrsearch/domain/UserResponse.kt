package com.tori.flickrsearch.domain

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("person") val person: User,
    @SerializedName("stat") val stat: String
)