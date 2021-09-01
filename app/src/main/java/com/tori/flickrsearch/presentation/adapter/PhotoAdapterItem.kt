package com.tori.flickrsearch.presentation.adapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoAdapterItem(
    val photoUrl: String,
    val title: String,
    val owner: String
) : Parcelable