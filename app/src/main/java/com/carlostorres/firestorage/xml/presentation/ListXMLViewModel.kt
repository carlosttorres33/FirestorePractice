package com.carlostorres.firestorage.xml.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlostorres.firestorage.data.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListXMLViewModel @Inject constructor(
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

data class ListUIState(
    val isLading : Boolean = false,
    val images : List<String> = emptyList()
)