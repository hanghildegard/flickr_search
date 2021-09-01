package com.tori.flickrsearch.presentation.photolist

import android.widget.ImageView
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import com.squareup.picasso.Picasso
import com.tori.flickrsearch.data.services.PhotoSearchService
import com.tori.flickrsearch.domain.PhotoResponse
import com.tori.flickrsearch.utils.FlickrUrlUtil
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.resumeWithException

class PhotoListPresenter(
    private val photoSearchService: PhotoSearchService,
    private val picasso: Lazy<Picasso>,
    mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MvpNullObjectBasePresenter<PhotoListView>(), CoroutineScope by CoroutineScope(mainDispatcher) {

    private var searchPhotoJob: Job? = null

    fun loadImage(url: String, imageView: ImageView) {
        picasso.value
            .load(url)
            .into(imageView)
    }

    fun fetchPhotos(text: String?) {
        searchPhotoJob = launch {
            try {
                if (!text.isNullOrEmpty()) {
                    val response = searchPhotos(text)
                    val firstImage = response.photos.photo.get(0)
                    view.showImage(FlickrUrlUtil.getPhotoUrl(firstImage))
                    //view.showResponse(response.toString())
                }
            } catch (e: Exception) {
            }
        }
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