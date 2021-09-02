package com.tori.flickrsearch.data.services

import com.tori.flickrsearch.domain.PhotoResponse
import com.tori.flickrsearch.domain.UserResponse
import io.reactivex.rxjava3.core.Single

interface PhotoSearchService {

    fun searchPhotos(text: String): Single<PhotoResponse>

    fun getUser(id: String): Single<UserResponse>
}