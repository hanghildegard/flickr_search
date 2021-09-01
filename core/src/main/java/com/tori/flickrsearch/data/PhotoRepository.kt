package com.tori.flickrsearch.data

import com.tori.flickrsearch.data.services.PhotoSearchApi
import com.tori.flickrsearch.data.services.PhotoSearchService

class PhotoRepository(val photoSearchService: PhotoSearchService) {

    suspend fun search(text: String) = photoSearchService.searchPhotos(text)
}