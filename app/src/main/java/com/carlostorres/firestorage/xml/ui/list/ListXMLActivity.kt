package com.carlostorres.firestorage.xml.ui.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.carlostorres.firestorage.databinding.ActivityListXmlactivityBinding
import com.carlostorres.firestorage.xml.presentation.ListXMLViewModel
import com.carlostorres.firestorage.xml.ui.list.adapter.GalleryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListXMLActivity : AppCompatActivity() {

    companion object {
        fun create(context : Context) = Intent(context, ListXMLActivity::class.java)
    }

    private lateinit var binding : ActivityListXmlactivityBinding
    private val viewModel : ListXMLViewModel by viewModels()
    private lateinit var galleryAdapter : GalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListXmlactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        viewModel.getAllImages()

    }

    private fun initUI() {
        initUIState()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        galleryAdapter = GalleryAdapter()
        binding.rvGallery.apply {
            layoutManager = GridLayoutManager(this@ListXMLActivity, 2)
            adapter = galleryAdapter
        }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{ uiState ->
                    binding.pbGallery.isVisible = uiState.isLading
                    galleryAdapter.updateList(uiState.images)
                }
            }
        }
    }
}