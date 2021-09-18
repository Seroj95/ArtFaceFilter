package com.ser.artface.repositories

import android.graphics.Bitmap
import android.net.Uri
import com.ser.artface.data.ImageFilter
import java.util.logging.Filter

interface EditImageRepository {
    suspend fun preaperImagePrewie(imageUri: Uri): Bitmap?
    suspend fun getImageFilters(image: Bitmap): List<ImageFilter>
    suspend fun savedFilteredImage(filteredBitmap: Bitmap): Uri?
}