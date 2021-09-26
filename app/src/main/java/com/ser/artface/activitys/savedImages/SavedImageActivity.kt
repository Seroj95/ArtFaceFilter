package com.ser.artface.activitys.savedImages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.FileProvider
import com.ser.artface.R
import com.ser.artface.activitys.editimage.EditImageActivity
import com.ser.artface.activitys.filteredImage.FilteredImageActivity
import com.ser.artface.adapters.SavedImagesAdapter
import com.ser.artface.databinding.ActivitySavedImageBinding
import com.ser.artface.listeners.SavedImageListener
import com.ser.artface.utilites.displayToast
import com.ser.artface.viewmodels.SavedImagesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class SavedImageActivity : AppCompatActivity(),SavedImageListener {
    private lateinit var binding: ActivitySavedImageBinding
    private val viewModel:SavedImagesViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySavedImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObserver()
        setListener()
        viewModel.loadSavedImages()
    }
    private fun setupObserver(){
        viewModel.savedImagesUiState.observe(this,{
            val  savedImagesDataState= it ?: return@observe
            binding.savedImagesProgresBar.visibility=
                if (savedImagesDataState.isLoading) View.VISIBLE else View.GONE
            savedImagesDataState.savedImages?.let {
                savedImagesDataState.savedImages?.let {  savedImages->
//                    displayToast("${savedImages.size}images loaded")
                    SavedImagesAdapter(savedImages,this).also { adapter ->
                        with(binding.savedImagesRecyclerView){
                            this.adapter=adapter
                            visibility=View.VISIBLE

                        }
                    }
                }?: run { savedImagesDataState.error?.let{ error -> displayToast(error) } }
            }
        })
    }
    private fun setListener(){
        binding.imageBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onImageCliked(file: File) {
        val fileUri=FileProvider.getUriForFile(
            applicationContext,"${packageName}.provider",file
        )
        Intent(
            applicationContext,
            FilteredImageActivity::class.java
        ).also { filteredImageIntent->
            filteredImageIntent.putExtra(EditImageActivity.KEY_FILTERED_IMAGE_URI,fileUri)
            startActivity(filteredImageIntent)
        }

    }
}