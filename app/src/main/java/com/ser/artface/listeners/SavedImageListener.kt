package com.ser.artface.listeners

import java.io.File

interface SavedImageListener {
    fun onImageCliked(file: File)
}