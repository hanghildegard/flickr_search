package com.tori.flickrsearch.utils

import com.tori.flickrsearch.domain.Photo
import java.lang.StringBuilder

class FlickrUrlUtil {

    companion object {
        const val BASE_URL = "https://live.staticflickr.com/"

        fun getPhotoUrl(photo: Photo): String {
            val builder = StringBuilder(BASE_URL)
            photo.apply {
                builder.append("/$server/${id}_${secret}_b.jpg")
            }
            return builder.toString()
        }
    }
}