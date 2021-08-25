package com.ser.artface.repositories

import android.graphics.Bitmap
import android.net.Uri

interface EditImageRepository {
    suspend fun preaperImagePrewie(imageUri: Uri):Bitmap?

}