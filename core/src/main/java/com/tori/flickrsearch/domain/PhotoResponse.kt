package com.tori.flickrsearch.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PhotoResponse(
    @SerializedName("photos") val photos: Photos,
    @SerializedName("stat") val stat: String
) : Serializable {

    override fun toString(): String {
        return "{photos: $photos, stat: $stat}"
    }
}