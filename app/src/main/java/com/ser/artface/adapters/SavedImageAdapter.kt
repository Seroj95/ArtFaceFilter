package com.ser.artface.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ser.artface.databinding.ItemContainerSavedImageBinding
import com.ser.artface.listeners.SavedImageListener
import com.ser.artface.viewmodels.SavedImagesViewModel
import java.io.File

class SavedImagesAdapter(
    private val savedImages: List<Pair<File, Bitmap>>,
    private val savedImageListener: SavedImageListener
) :
    RecyclerView.Adapter<SavedImagesAdapter.SavedImageViewHolder>() {
    inner class SavedImageViewHolder(val binding: ItemContainerSavedImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedImageViewHolder {
        val binding = ItemContainerSavedImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SavedImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedImageViewHolder, position: Int) {
        with(holder) {
            with(savedImages[position]) {
                binding.imageSaved.setImageBitmap(second)
                binding.imageSaved.setOnClickListener {
                    savedImageListener.onImageCliked(first)
                }
            }
        }
    }

    override fun getItemCount(): Int = savedImages.size


}