package com.tori.flickrsearch.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.lang.StringBuilder

data class Photos(
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("perpage") val perPage: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("photo") val photo: List<Photo>
) : Serializable {

    override fun toString(): String {
        val builder = StringBuilder("{page: $page, pages: $pages, perpage: $perPage, total: $total, photo: [")
        for (p in photo) {
            builder.append(p.toString())
        }
        builder.append("]}")
        return builder.toString()
    }
}