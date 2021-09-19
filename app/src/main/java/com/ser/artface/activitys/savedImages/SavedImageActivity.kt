package com.ser.artface.activitys.savedImages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ser.artface.R
import com.ser.artface.adapters.SavedImagesAdapter
import com.ser.artface.databinding.ActivitySavedImageBinding
import com.ser.artface.utilites.displayToast
import com.ser.artface.viewmodels.SavedImagesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SavedImageActivity : AppCompatActivity() {
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
                    SavedImagesAdapter(savedImages).also { adapter ->
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
}