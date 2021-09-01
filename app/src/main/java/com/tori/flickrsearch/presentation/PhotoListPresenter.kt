package com.tori.flickrsearch.presentation.photolist

import android.widget.ImageView
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import com.squareup.picasso.Picasso
import com.tori.flickrsearch.data.services.PhotoSearchService
import com.tori.flickrsearch.domain.PhotoResponse
import com.tori.flickrsearch.presentation.adapter.PhotoAdapterItem
import com.tori.flickrsearch.utils.FlickrUrlUtil
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.resumeWithException

class PhotoListPresenter(
    private val photoSearchService: PhotoSearchService,
    mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MvpNullObjectBasePresenter<PhotoListView>(), CoroutineScope by CoroutineScope(mainDispatcher) {

    private var searchPhotoJob: Job? = null

    fun fetchPhotos(text: String?) {
        searchPhotoJob = launch {
            try {
                if (!text.isNullOrEmpty()) {
                    parseResponse(searchPhotos(text))
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun parseResponse(response: PhotoResponse) {
        val listItems = ArrayList<PhotoAdapterItem>()
        for (photo in response.photos.photo) {
            listItems.add(PhotoAdapterItem(FlickrUrlUtil.getPhotoUrl(photo), photo.title, photo.owner))
        }
        view.showItems(listItems)
    }

    private suspend fun searchPhotos(text: String): PhotoResponse =
        withContext(ioDispatcher) {
            suspendCancellableCoroutine<PhotoResponse> { continuation ->
                val request = photoSearchService
                    .searchPhotos(text)
                    .subscribe(
                        { response ->
                            continuation.resumeWith(Result.success(response))
                        },
                        { error -> continuation.resumeWithException(error) }
                    )
                continuation.invokeOnCancellation { request.dispose() }
            }
        }

}