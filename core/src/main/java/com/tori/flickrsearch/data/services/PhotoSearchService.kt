package com.tori.flickrsearch.data.services

import com.tori.flickrsearch.domain.PhotoResponse
import io.reactivex.rxjava3.core.Single

interface PhotoSearchService {

    fun searchPhotos(text: String): Single<PhotoResponse>
}