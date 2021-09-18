package com.ser.artface.activitys.filteredImage

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ser.artface.R
import com.ser.artface.activitys.editimage.EditImageActivity
import com.ser.artface.databinding.ActivityFilteredImageBinding

class FilteredImageActivity : AppCompatActivity() {
    private lateinit var fileUri:Uri
    private lateinit var binding: ActivityFilteredImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFilteredImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        displayFilteredImage()
        setListener()
    }
    private fun displayFilteredImage(){
        intent.getParcelableExtra<Uri>(EditImageActivity.KEY_FILTERED_IMAGE_URI)?.let { imageUri ->
            fileUri=imageUri
            binding.imageFilteredImage.setImageURI(imageUri)

        }
    }
    private  fun setListener(){
        binding.fabShare.setOnClickListener {
            with(Intent(Intent.ACTION_SEND)){
                putExtra(Intent.EXTRA_STREAM,fileUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                type="image/*"
                startActivity(this)
            }
        }
    }
}