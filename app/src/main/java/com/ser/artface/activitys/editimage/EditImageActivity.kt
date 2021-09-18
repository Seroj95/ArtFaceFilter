package com.ser.artface.activitys.editimage

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.ser.artface.activitys.filteredImage.FilteredImageActivity
import com.ser.artface.activitys.main.MainActivity
import com.ser.artface.adapters.ImageFiltersAdapter
import com.ser.artface.data.ImageFilter
import com.ser.artface.databinding.ActivityEditImageBinding
import com.ser.artface.listeners.ImageFilterListener
import com.ser.artface.utilites.displayToast
import com.ser.artface.utilites.show
import com.ser.artface.viewmodels.EditImageViewModel
import jp.co.cyberagent.android.gpuimage.GPUImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditImageActivity : AppCompatActivity(), ImageFilterListener {

companion object{
    const val KEY_FILTERED_IMAGE_URI="filteredImageUri"
}

    private lateinit var binding: ActivityEditImageBinding
    private val viewModel: EditImageViewModel by viewModel()
    private lateinit var gpuImage: GPUImage

    //ImageBitmap
    private lateinit var originalBitmap: Bitmap
    private val filterBitmap = MutableLiveData<Bitmap>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
//        dispalyImagePreview()
        setupObservers()
        prepareImagePreview()
    }

    private fun setupObservers() {
        viewModel.ImagePreviewUiState.observe(this, {
            val dataState = it ?: return@observe
            binding.previwProgresBar.visibility =
                if (dataState.isLoading) View.VISIBLE else View.GONE
            dataState.bitmap?.let { bitmap ->

                //For the firest time'filtered image=original image"
                originalBitmap = bitmap
                filterBitmap.value = bitmap
                with(originalBitmap) {
                    gpuImage.setImage(this)
                    binding.imagePreview.show()
                    viewModel.loadImageFilters(this)

                }
//                binding.imagePreview.setImageBitmap(bitmap)

            } ?: kotlin.run {
                dataState.error?.let { error ->
                    displayToast(error)

                }
            }
        })
        viewModel.imageFiltersUIState.observe(this, {
            val imageFiltersDataState = it ?: return@observe
            binding.imageFilterProgresBar.visibility =
                if (imageFiltersDataState.isLoading) View.VISIBLE else View.GONE
            imageFiltersDataState.imageFilters?.let { imageFilters ->
                ImageFiltersAdapter(imageFilters, this).also { adapter ->
                    binding.filtersRecyclerView.adapter = adapter

                }
            } ?: kotlin.run {
                imageFiltersDataState.error?.let { error ->
                    displayToast(error)

                }
            }
        })
        filterBitmap.observe(this
        ,{bitmap -> binding.imagePreview.setImageBitmap(bitmap)})
        viewModel.saveFilteredImageUIState.observe(this,{
            val saveFilteredImageDataState=it ?: return@observe
            if (saveFilteredImageDataState.isLoading){
                binding.imageSave.visibility=View.GONE
                binding.saveingProgresBar.visibility=View.VISIBLE
            }else{
                binding.saveingProgresBar.visibility=View.GONE
                binding.imageSave.visibility=View.VISIBLE
            }
            saveFilteredImageDataState.uri?.let { savedImageUri->
Intent(
    applicationContext,FilteredImageActivity::class.java
).also { filteredImageIntent ->
    filteredImageIntent.putExtra(KEY_FILTERED_IMAGE_URI,savedImageUri)
    startActivity(filteredImageIntent)

}
            } ?: kotlin.run {
                saveFilteredImageDataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })
    }

    private fun prepareImagePreview() {
        gpuImage = GPUImage(applicationContext)
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let { imageUri ->
            viewModel.prepareImagePreview(imageUri)
        }
    }


    private fun setListeners() {
        binding.imageBack.setOnClickListener {
            onBackPressed()

        }
        binding.imageSave.setOnClickListener {
            filterBitmap.value?.let { bitmap ->
                viewModel.saveFilteredImage(bitmap)
            }
        }
        /*
        This will original image when we long clik the Image view untile we realise
        So  that we can see diference betwen original image
         */
        binding.imagePreview.setOnLongClickListener {
            binding.imagePreview.setImageBitmap(originalBitmap)
            return@setOnLongClickListener false
        }
        binding.imagePreview.setOnClickListener {
            binding.imagePreview.setImageBitmap(filterBitmap.value)
        }
    }

    override fun onFilterSelected(imagFilter: ImageFilter) {
with(imagFilter){
    with(gpuImage){
        setFilter(filter)
        filterBitmap.value=bitmapWithFilterApplied
    }
}
    }

}