package com.carlostorres.firestorage.compose.ui.upload

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.carlostorres.firestorage.R
import com.carlostorres.firestorage.compose.presentation.UploadComposeViewModel
import com.carlostorres.firestorage.databinding.ActivityUploadComposeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@AndroidEntryPoint
class UploadComposeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadComposeBinding

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
    fun UploadScreen(
        viewModel : UploadComposeViewModel = hiltViewModel()
    ) {

        var uri: Uri? by remember {
            mutableStateOf(null)
        }

        var intentCameraLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { result ->
                if (result && uri?.path?.isNotBlank() == true) {
                    viewModel.uploadBasicImage(uri!!)
                }
            }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            FloatingActionButton(
                onClick = {
                    uri = generateUri()
                    intentCameraLauncher.launch(uri!!)
                },
                backgroundColor = colorResource(id = R.color.green),
                shape = RoundedCornerShape(16.dp)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = "",
                    tint = Color.White
                )

            }

        }

    }

    private fun generateUri(): Uri{
        return FileProvider.getUriForFile(
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
