package com.kednections.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import androidx.core.graphics.scale

/**
 * IMAGEVIEW EXTENSIONS
 */
fun decodeStringToBitmap(encodedBitmap: String?): Bitmap? {
    var decodedImage: Bitmap? = null
    encodedBitmap?.let {
        val imageBytes = Base64.decode(it, Base64.DEFAULT)
        decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
    return decodedImage
}

//[yellow] уточнить размеры картинки - во фрагменте они с процентами. занести их в константы в отдельный файл
fun encodeBitmapToString(bm: Bitmap): String {
    val min = Math.min(bm.width, bm.height)
    val scaledWidth = (500 * (bm.width.toFloat() / min)).toInt()
    val scaledHeight = (500 * (bm.height.toFloat() / min)).toInt()
//    val scaledBitmap = Bitmap.createScaledBitmap(bm, scaledWidth, scaledHeight, false)
    val scaledBitmap = bm.scale(scaledWidth, scaledHeight, false)
    val baos = ByteArrayOutputStream()
    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
    return encodedImage.toString()
}