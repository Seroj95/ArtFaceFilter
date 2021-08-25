package com.ser.artface.viewmodels

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ser.artface.repositories.EditImageRepository
import com.ser.artface.utilites.Coroutines

class EditImageViewModel(private val editImageRepository: EditImageRepository) : ViewModel() {
    private val imagePreviewDataState = MutableLiveData<ImagePreviewDataState>()
    fun prepareImagePreview(imageUri: Uri) {
        Coroutines.io {
            kotlin.runCatching {
                ImagePreviwemitUiState(isLoading = true)
                editImageRepository.preaperImagePrewie(imageUri)

            }.onSuccess { bitmap ->
                if (bitmap != null) {
                    ImagePreviwemitUiState(bitmap = bitmap)

                } else {
                    ImagePreviwemitUiState(error = "Unable to prepare image preview")
                }
            }.onFailure {
                ImagePreviwemitUiState(error = it.message.toString())
            }
        }
    }

    val ImagePreviewUiState: LiveData<ImagePreviewDataState>
        get() =
            imagePreviewDataState

    private fun ImagePreviwemitUiState(
        isLoading: Boolean = false,
        bitmap: Bitmap? = null,
        error: String? = null
    ) {
        val dataState = ImagePreviewDataState(isLoading, bitmap, error)
        imagePreviewDataState.postValue(dataState)
    }

    data class ImagePreviewDataState(
        val isLoading: Boolean,
        val bitmap: Bitmap?,
        val error: String?
    )
}