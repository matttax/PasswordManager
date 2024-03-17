package com.matttax.passwordmanager.passwords.icons

import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IconStorage @Inject constructor() {

    fun saveToInternalStorage(path: String, bitmapImage: Bitmap) {
        val fos = FileOutputStream(path)
        try {
            bitmapImage.compress(Bitmap.CompressFormat.PNG, DEFAULT_QUALITY, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fos.close()
        }
    }

    fun remove(path: String) {
        val file = File(path)
        try {
            file.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        const val DEFAULT_QUALITY = 100
    }
}
