package com.tori.flickrsearch.domain

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("nsid") val nsId: String,
    @SerializedName("username") val userName: UserDetail,
    @SerializedName("realname") val realName: UserDetail,
    @SerializedName("location") val location: UserDetail,
    @SerializedName("description") val description: UserDetail,
    @SerializedName("profileurl") val profileUrl: UserDetail,
    @SerializedName("mobileurl") val mobileUrl: UserDetail
)