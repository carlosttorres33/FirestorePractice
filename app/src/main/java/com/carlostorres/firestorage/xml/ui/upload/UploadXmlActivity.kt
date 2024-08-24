package com.carlostorres.firestorage.xml.ui.upload

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.carlostorres.firestorage.MainActivity
import com.carlostorres.firestorage.databinding.ActivityUploadXmlBinding
import com.carlostorres.firestorage.xml.presentation.UploadXmlViewModel
import dagger.hilt.android.AndroidEntryPoint
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
    private var intentCameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()){ result ->
        if (result && uri.path?.isNotBlank() == true){
            uploadViewModel.uploadBasicImage(uri)
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
    }

    private fun initListeners() {
        binding.fabImage.setOnClickListener {
            takePhoto()
        }
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

}