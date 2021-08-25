package com.ser.artface.activitys.editimage

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ser.artface.activitys.main.MainActivity
import com.ser.artface.databinding.ActivityEditImageBinding
import com.ser.artface.utilites.displayToast
import com.ser.artface.utilites.show
import com.ser.artface.viewmodels.EditImageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditImageBinding
    private val viewModel:EditImageViewModel by viewModel()
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
            binding.imagePreview.setImageBitmap(bitmap)
            ///karoxa sxal lini show() kdardznes
            binding.imagePreview.show()
        } ?: kotlin.run {
            dataState.error?.let { error ->
                displayToast(error)

            }
        }
    })
}
    private fun prepareImagePreview() {
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let { imageUri ->
            viewModel.prepareImagePreview(imageUri)
        }
}



    private fun setListeners() {
        binding.imageBack.setOnClickListener {
            onBackPressed()
        }
    }

}