package com.tori.flickrsearch.data.services

import com.tori.flickrsearch.domain.PhotoResponse
import com.tori.flickrsearch.domain.UserResponse
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

    @GET("/services/rest")
    fun getUser(
        @Query("method") method: String = "flickr.people.getInfo",
        @Query("user_id") userId: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1
    ): Single<UserResponse>
}