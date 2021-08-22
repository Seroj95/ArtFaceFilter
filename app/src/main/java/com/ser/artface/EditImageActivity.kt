package com.ser.artface

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ser.artface.databinding.ActivityEditImageBinding

class EditImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
dispalyImagePreview()
    }
private  fun dispalyImagePreview(){
    intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let { imageUri ->
        val inputStream=contentResolver.openInputStream(imageUri)
        val bitmap=BitmapFactory.decodeStream(inputStream)
        binding.imagePreview.setImageBitmap(bitmap)
        binding.imagePreview.visibility= View.VISIBLE

    }
}
    private fun setListeners() {
        binding.imageBack.setOnClickListener {
            onBackPressed()
        }
    }

}