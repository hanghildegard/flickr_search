package com.tori.flickrsearch.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Photo(
    @SerializedName("id") val id: String,
    @SerializedName("owner") val owner: String,
    @SerializedName("secret") val secret: String,
    @SerializedName("server") val server: String,
    @SerializedName("title") val title: String,
    @SerializedName("ispublic") val isPublic: Int,
    @SerializedName("isfriend") val isFriend: Int,
    @SerializedName("isfamily") val isFamily: Int
) : Serializable {

    override fun toString(): String {
        return "{id: $id, owner: $owner, secret: $secret, server: $title, title: $title, ispublic: $isPublic, isfriend: $isFriend, isfamily: $isFamily}"
    }
}