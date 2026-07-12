package com.example.pawfect_mobile.ui.components

import android.net.Uri

sealed class ImageInputValue {
    object Empty : ImageInputValue()
    data class Url(val url: String) : ImageInputValue()
    data class LocalUri(val uri: Uri) : ImageInputValue()
}
