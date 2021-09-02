package com.tori.flickrsearch.presentation.photolist

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.tori.flickrsearch.presentation.adapter.PhotoAdapterItem

interface PhotoListView : MvpView {
    fun showContent(show: Boolean)

    fun showLoading(show: Boolean)

    fun showError(show: Boolean)

    fun clearItems()

    fun showItems(items: List<PhotoAdapterItem>)
}