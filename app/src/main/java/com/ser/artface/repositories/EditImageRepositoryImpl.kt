package com.ser.artface.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.InputStream

class EditImageRepositoryImpl(private val context:Context):EditImageRepository {
    override suspend fun preaperImagePrewie(imageUri: Uri): Bitmap? {
       getInputStreamFromUri(imageUri)?.let { inputStream ->
           val originalBitam=BitmapFactory.decodeStream(inputStream)
       val width=context.resources.displayMetrics.widthPixels
           val height=((originalBitam.height * width)/originalBitam.width)
           return Bitmap.createScaledBitmap(originalBitam,width,height,false)
       } ?: return null
    }
    private fun getInputStreamFromUri(uri: Uri):InputStream?{
        return context.contentResolver.openInputStream(uri)

    }

}