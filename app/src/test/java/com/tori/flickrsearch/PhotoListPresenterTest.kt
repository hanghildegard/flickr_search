package com.tori.flickrsearch

import com.tori.flickrsearch.data.services.PhotoSearchService
import com.tori.flickrsearch.domain.*
import com.tori.flickrsearch.presentation.adapter.PhotoAdapterItem
import com.tori.flickrsearch.presentation.photolist.PhotoListPresenter
import com.tori.flickrsearch.presentation.photolist.PhotoListView
import com.tori.flickrsearch.testutils.CoroutinesTestRule
import io.reactivex.rxjava3.core.Single
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@RunWith(MockitoJUnitRunner::class)
class PhotoListPresenterTest {

    private lateinit var presenter: PhotoListPresenter
    private val view = mock<PhotoListView>()

    private val photoSearchService = mock<PhotoSearchService>()

    @JvmField
    @Rule
    val testRule = CoroutinesTestRule()
    private val ioDispatcher = TestCoroutineDispatcher()

    private val listItemsCaptor = argumentCaptor<List<PhotoAdapterItem>>()

    private val photoResponse = PhotoResponse(
        photos = Photos(
            page = 1, pages = 1, perPage = 100, total = 2, photo = listOf(
                Photo(
                    id = "id1",
                    owner = "owner1",
                    secret = "secret1",
                    server = "server",
                    title = "Photo 1",
                    isPublic = 1,
                    isFamily = 0,
                    isFriend = 0
                ),
                Photo(
                    id = "id2",
                    owner = "owner2",
                    secret = "secret2",
                    server = "server",
                    title = "Photo 2",
                    isPublic = 1,
                    isFamily = 0,
                    isFriend = 0
                )
            )
        ), stat = "ok"
    )
    private val emptyPhotoResponse = PhotoResponse(
        photos = Photos(
            page = 1,
            pages = 1,
            perPage = 100,
            total = 0,
            photo = ArrayList()
        ), stat = "ok"
    )
    private val userResponse = UserResponse(
        person = User(
            id = "id",
            nsId = "id",
            userName = UserDetail(content = "john"),
            realName = UserDetail(content = "John Doe"),
            location = UserDetail(content = "New York"),
            description = UserDetail(content = "description"),
            profileUrl = UserDetail(content = "profileurl"),
            mobileUrl = UserDetail("mobileurl")
        ), stat = "ok"
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        presenter = PhotoListPresenter(
            photoSearchService,
            mainDispatcher = testRule.testDispatcher,
            ioDispatcher = ioDispatcher
        )
        presenter.attachView(view)
    }

    @Test
    fun `test viewCreated with no saved instance`() {
        presenter.onViewCreated(null)

        verify(view, never()).showLoading(true)
        verify(view, never()).showContent(true)
    }

    @Test
    fun `test viewCreated with restored data`() {
        whenever(photoSearchService.searchPhotos(anyString())).thenReturn(Single.just(photoResponse))
        whenever(photoSearchService.getUser(anyString())).thenReturn(Single.just(userResponse))

        presenter.onViewCreated("search text")

        verify(view).showLoading(true)
        verify(view).showContent(true)
        verify(view).showItems(listItemsCaptor.capture())
        val listItems = listItemsCaptor.lastValue
        assertEquals(2, listItems.size)
    }

    @Test
    fun `test onSearchTextChanged when text is cleared`() {
        presenter.onSearchTextChanged("")

        verify(view, never()).showLoading(true)
        verify(view).clearItems()
        verify(view).showContent(false)
        verify(view).updateEmptyLabel(R.string.empty_text)
        verify(view).showEmpty(true)
    }

    @Test
    fun `test onSearchClicked with valid search text`() {
        whenever(photoSearchService.searchPhotos(anyString())).thenReturn(Single.just(photoResponse))
        whenever(photoSearchService.getUser(anyString())).thenReturn(Single.just(userResponse))

        presenter.onSearchClicked("search text")

        verify(view).showLoading(true)
        verify(view).showContent(true)
        verify(view).showItems(listItemsCaptor.capture())
        val listItems = listItemsCaptor.lastValue
        assertEquals(2, listItems.size)
    }

    @Test
    fun `test onSearchClicked with empty search text`() {
        presenter.onSearchClicked("")

        verify(view, never()).showLoading(true)
        verify(view, never()).showContent(true)
        verify(view).clearItems()
        verify(view).updateEmptyLabel(R.string.empty_text)
        verify(view).showEmpty(true)
    }

    @Test
    fun `test fetchPhotos failed`() {
        whenever(photoSearchService.searchPhotos(anyString())).thenReturn(Single.error(Exception("error")))

        presenter.onSearchClicked("search text")

        verify(view).showLoading(true)
        verify(view).showError(true)
    }

    @Test
    fun `test fetchPhotos with empty result`() {
        whenever(photoSearchService.searchPhotos(anyString())).thenReturn(Single.just(emptyPhotoResponse))

        presenter.onSearchClicked("search text")

        verify(view).showLoading(true)
        verify(view).updateEmptyLabel(R.string.no_result_text)
        verify(view).showEmpty(true)
    }
}