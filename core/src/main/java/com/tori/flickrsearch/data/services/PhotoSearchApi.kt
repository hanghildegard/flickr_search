package com.tori.flickrsearch.data.services

import com.tori.flickrsearch.domain.PhotoResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoSearchApi {

    @GET("/services/rest")
    fun searchPhotos(
        @Query("method") method: String = "flickr.photos.search",
        @Query("text") text: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1
    ): Single<PhotoResponse>
}