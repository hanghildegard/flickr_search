package com.tori.flickrsearch.presentation.photolist

import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import com.tori.flickrsearch.data.services.PhotoSearchService
import com.tori.flickrsearch.domain.Photo
import com.tori.flickrsearch.domain.PhotoResponse
import com.tori.flickrsearch.domain.UserResponse
import com.tori.flickrsearch.presentation.adapter.PhotoAdapterItem
import com.tori.flickrsearch.utils.FlickrUrlUtil
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.resumeWithException

class PhotoListPresenter(
    private val photoSearchService: PhotoSearchService,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MvpNullObjectBasePresenter<PhotoListView>(), CoroutineScope by CoroutineScope(mainDispatcher) {

    private var searchPhotoJob: Job? = null

    fun fetchPhotos(text: String?) {
        if (!text.isNullOrEmpty()) {
            searchPhotoJob = launch {
                view.showLoading(true)
                try {
                    parseResponse(searchPhotos(text))

                } catch (e: Exception) {
                    view.showLoading(false)
                    view.showError(true)
                }
            }
        } else {

        }
    }

    private suspend fun parseResponse(response: PhotoResponse) {
        coroutineScope {
            val listItems = response.photos.photo.map { photo ->
                async { getPhotoItem(photo, getUserInfo(photo.owner)) }
            }
                .awaitAll()
                .toList()
            withContext(mainDispatcher) {
                view.showLoading(false)
                view.showItems(listItems)
            }
        }
    }

    private fun getPhotoItem(photo: Photo, user: UserResponse): PhotoAdapterItem {
        var userName = ""
        try {
            userName = user.person.realName.content ?: user.person.userName.content ?: photo.owner
            if (userName.isEmpty()) userName = photo.owner
        } catch (e: Exception) {
            userName = photo.owner
        } finally {
            return PhotoAdapterItem(FlickrUrlUtil.getPhotoUrl(photo), photo.title, userName)
        }
    }

    private suspend fun getUserInfo(userId: String): UserResponse =
        withContext(ioDispatcher) {
            suspendCancellableCoroutine { continuation ->
                val request = photoSearchService
                    .getUser(userId)
                    .subscribe(
                        { response ->
                            continuation.resumeWith(Result.success(response))
                        },
                        { error -> continuation.resumeWithException(error) }
                    )
                continuation.invokeOnCancellation { request.dispose() }
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