package com.example.photo.picture.model

import android.net.Uri

data class ImageData(
    val id: Long,
    val title: String,
    val date: Long,
    val uri: Uri,
    var checked: Boolean = false
)
