package com.carlostorres.firestorage.xml.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.carlostorres.firestorage.databinding.ItemGalleryBinding
import com.carlostorres.firestorage.xml.ui.list.holder.GalleryViewHolder

class GalleryAdapter (
    private val images : MutableList<String> = mutableListOf()
) : RecyclerView.Adapter<GalleryViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {

        val binding = ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GalleryViewHolder(binding)

    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {

        holder.render(images[position])

    }

    fun updateList(list: List<String>){
        images.clear()
        images.addAll(list)
        notifyDataSetChanged()
    }

}