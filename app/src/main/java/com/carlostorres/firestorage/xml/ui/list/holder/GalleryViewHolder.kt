package com.carlostorres.firestorage.xml.ui.list.holder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carlostorres.firestorage.databinding.ItemGalleryBinding

class GalleryViewHolder(
    private val binding : ItemGalleryBinding
) : RecyclerView.ViewHolder(binding.root){

    fun render(uriImage : String){

        Glide
            .with(binding.ivGalleryItem.context)
            .load(uriImage)
            .into(binding.ivGalleryItem)
    }

}