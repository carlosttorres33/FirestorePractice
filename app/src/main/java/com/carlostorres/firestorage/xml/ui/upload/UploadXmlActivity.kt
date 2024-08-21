package com.carlostorres.firestorage.xml.ui.upload

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.carlostorres.firestorage.MainActivity
import com.carlostorres.firestorage.R
import com.carlostorres.firestorage.databinding.ActivityUploadXmlBinding
import com.carlostorres.firestorage.xml.presentation.UploadXmlViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadXmlActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    private lateinit var binding : ActivityUploadXmlBinding

    private val uploadViewModel: UploadXmlViewModel by viewModels()

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
        TODO("Not yet implemented")
    }

}