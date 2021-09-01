package com.tori.flickrsearch.data.services

import com.tori.flickrsearch.domain.PhotoResponse
import io.reactivex.rxjava3.core.Single

class PhotoSearchServiceHandler(private val photoSearchApi: PhotoSearchApi) : PhotoSearchService {

    override fun searchPhotos(text: String): Single<PhotoResponse> = photoSearchApi.searchPhotos(text = text)

}