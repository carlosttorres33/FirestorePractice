package com.carlostorres.firestorage.compose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlostorres.firestorage.data.StorageService
import com.carlostorres.firestorage.xml.presentation.ListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListComposeViewModel @Inject constructor(
    private val storageService: StorageService
) : ViewModel() {

    private var _uiState = MutableStateFlow(ListUIState())
    val uiState : StateFlow<ListUIState> = _uiState

    fun getAllImages() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLading = true)

            val result = withContext(Dispatchers.IO){
                storageService.getAllImages().map {
                    it.toString()
                }
            }

            _uiState.value = _uiState.value.copy(isLading = false, images = result)
        }
    }

}

