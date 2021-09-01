package com.tori.flickrsearch.presentation.photolist

import android.util.Log
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import com.tori.flickrsearch.data.services.PhotoSearchService
import com.tori.flickrsearch.domain.PhotoResponse
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.function.Consumer
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
                    val response = searchPhotos(text)
                    view.showResponse(response.toString())
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