package com.matttax.passwordmanager.passwords.icons

import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebView
import java.io.File
import java.util.*

class IconObserver(
    defaultDirectory: File
) : WebChromeClient() {

    private val name = UUID.randomUUID().toString().take(30)

    var icon: Bitmap? = null
        private set
    val path = File(defaultDirectory, "$name.jpg")

    override fun onReceivedIcon(view: WebView, icon: Bitmap) {
        super.onReceivedIcon(view, icon)
        this.icon = icon
    }
}
