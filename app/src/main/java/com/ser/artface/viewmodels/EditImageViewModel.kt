package com.ser.artface.viewmodels

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ser.artface.data.ImageFilter
import com.ser.artface.repositories.EditImageRepository
import com.ser.artface.utilites.Coroutines

class EditImageViewModel(private val editImageRepository: EditImageRepository) : ViewModel() {
    //prepare image view mode
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

    fun loadImageFilters(originalImage: Bitmap){
        Coroutines.io {
            runCatching {
                emitImageFiltersUiState(isLoading = true)
                editImageRepository.getImageFilters(getPreviewImage(originalImage))

            }.onSuccess { imageFilters->
                emitImageFiltersUiState(imageFilters = imageFilters)

            }.onFailure {
                emitImageFiltersUiState(error = it.message.toString())
            }
        }
    }

    private fun ImagePreviwemitUiState(
        isLoading: Boolean = false,
        bitmap: Bitmap? = null,
        error: String? = null
    ) {
        val dataState = ImagePreviewDataState(isLoading, bitmap, error)
        imagePreviewDataState.postValue(dataState)
    }

    private fun getPreviewImage(originalImage:Bitmap):Bitmap{
        return runCatching {
            val previewWidh=150
            val previewHeight=originalImage.height*previewWidh/originalImage.width
            Bitmap.createScaledBitmap(originalImage,previewWidh,previewHeight,false)
        }.getOrDefault(originalImage)
    }
private fun emitImageFiltersUiState(
    isLoading: Boolean=false,
    imageFilters: List<ImageFilter>?=null,
    error: String?=null
){
    val dataState=ImageFiltersDataState(isLoading,imageFilters, error)
    imageFiltersDataState.postValue(dataState)

}
    data class ImagePreviewDataState(
        val isLoading: Boolean,
        val bitmap: Bitmap?,
        val error: String?
    )
    //region:Load image filters
    private val imageFiltersDataState = MutableLiveData<ImageFiltersDataState>()
    val imageFiltersUIState:LiveData<ImageFiltersDataState> get() =imageFiltersDataState
    data class ImageFiltersDataState(
        val isLoading:Boolean,
        val imageFilters:List<ImageFilter>?,
        val error:String?
    )
    //endregion
}