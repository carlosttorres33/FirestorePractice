package com.carlostorres.firestorage.xml.presentation

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlostorres.firestorage.data.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UploadXmlViewModel @Inject constructor(
    private val storageService: StorageService
) : ViewModel() {

    private var _isLoading = MutableStateFlow(false)
    val isLoading : StateFlow<Boolean> = _isLoading

    fun uploadBasicImage(uri: Uri) {

        storageService.uploadBasicImage(uri)

    }

    fun uploadAndGetImage(uri: Uri, onSuccessDownload: (Uri) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = withContext(Dispatchers.IO) {
                    storageService.uploadAndDownloadImage(uri)
                }

                onSuccessDownload(result)
            } catch (e: Exception) {
                Log.e("UploadXmlViewModel", e.message.orEmpty())
            }
            _isLoading.value = false
        }
    }


}