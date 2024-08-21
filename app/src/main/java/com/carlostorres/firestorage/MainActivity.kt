package com.carlostorres.firestorage

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.carlostorres.firestorage.databinding.ActivityMainBinding
import com.carlostorres.firestorage.xml.ui.upload.UploadXmlActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNavToXml.setOnClickListener {
            startActivity(UploadXmlActivity.create(this))
        }

        binding.btnNavToComp.setOnClickListener {

        }

    }
}