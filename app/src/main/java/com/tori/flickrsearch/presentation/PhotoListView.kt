package com.tori.flickrsearch.presentation.photolist

import com.hannesdorfmann.mosby3.mvp.MvpView

interface PhotoListView : MvpView {
    fun showResponse(response: String)
}