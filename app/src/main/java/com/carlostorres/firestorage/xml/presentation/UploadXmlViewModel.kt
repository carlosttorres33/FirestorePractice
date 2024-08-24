package com.carlostorres.firestorage.xml.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.carlostorres.firestorage.data.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UploadXmlViewModel @Inject constructor(
    private val storageService: StorageService
) : ViewModel() {

    fun uploadBasicImage(uri: Uri) {

        storageService.uploadBasicImage(uri)

    }


}