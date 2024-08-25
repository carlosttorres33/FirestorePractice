package com.carlostorres.firestorage.xml.ui.upload

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.carlostorres.firestorage.MainActivity
import com.carlostorres.firestorage.databinding.ActivityUploadXmlBinding
import com.carlostorres.firestorage.databinding.DialogImageSelectorBinding
import com.carlostorres.firestorage.xml.presentation.UploadXmlViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@AndroidEntryPoint
class UploadXmlActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent = Intent(context, UploadXmlActivity::class.java)
    }

    private lateinit var binding : ActivityUploadXmlBinding

    private val uploadViewModel: UploadXmlViewModel by viewModels()

    private lateinit var uri : Uri
    private var intentCameraLauncher = registerForActivityResult(TakePicture()){ result ->
        if (result && uri.path?.isNotBlank() == true){
            uploadViewModel.uploadAndGetImage(uri){ downloadUri ->
                showNewImage(downloadUri)
            }
        }
    }

    private var intentGalleryLauncher = registerForActivityResult(GetContent()){ uriResult ->
        uriResult?.let {
            uploadViewModel.uploadAndGetImage(it){ downloadUri ->
                showNewImage(downloadUri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityUploadXmlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

    }

    private fun initUI() {
        initListeners()
        initUIState()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                uploadViewModel.isLoading.collect{
                    binding.pbImage.isVisible = it
                    if (it){
                        binding.ivPlaceholder.isVisible = false
                        binding.ivImage.setImageDrawable(null)
                    }
                }
            }
        }
    }

    private fun initListeners() {
        binding.fabImage.setOnClickListener {
            showImageDialog()
        }
    }

    private fun showImageDialog() {

        val dialogBinding = DialogImageSelectorBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this).apply {
            setView(dialogBinding.root)
        }.create()

        dialogBinding.btnTakePhoto.setOnClickListener {
            takePhoto()
            alertDialog.dismiss()
        }

        dialogBinding.btnGallery.setOnClickListener {
            getImageFromGallery()
            alertDialog.dismiss()
        }

        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        alertDialog.show()

    }

    private fun getImageFromGallery() {
        intentGalleryLauncher.launch("image/*")
    }

    private fun takePhoto(){
        generateUri()
        intentCameraLauncher.launch(uri)
    }

    private fun generateUri(){
        uri = FileProvider.getUriForFile(
            Objects.requireNonNull(this),
            "com.carlostorres.firestorage.provider",
            createFile()
        )
    }

    private fun createFile() : File {
        val name : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) + "image"
        return File.createTempFile(name, ".jpg", externalCacheDir)
    }

    private fun showNewImage(downloadUri: Uri) {
        Glide
            .with(this)
            .load(downloadUri)
            .into(binding.ivImage)
    }


}