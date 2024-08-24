package com.carlostorres.firestorage.compose.ui.upload

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.carlostorres.firestorage.R
import com.carlostorres.firestorage.databinding.ActivityUploadComposeBinding

class UploadComposeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUploadComposeBinding

    companion object {
        fun create(context: Context): Intent = Intent(context, UploadComposeActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadComposeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeView.setContent {

            UploadScreen()

        }
    }

    @Composable
    fun UploadScreen() {


        
    }
    
}
