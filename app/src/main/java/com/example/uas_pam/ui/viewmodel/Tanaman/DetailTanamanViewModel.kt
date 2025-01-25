package com.example.uas_pam.ui.viewmodel.Tanaman

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.Model.Tanaman
import com.example.uas_pam.Repository.TanamanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailTanamanUiState {
    object Loading : DetailTanamanUiState()
    data class Success(val tanaman: Tanaman) : DetailTanamanUiState()
    object Error : DetailTanamanUiState()
}

class DetailTanamanViewModel(private val tanamanRepository: TanamanRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<DetailTanamanUiState>(DetailTanamanUiState.Loading)
    val uiState: StateFlow<DetailTanamanUiState> = _uiState

    fun getTanamanById(idTanaman: String) {
        viewModelScope.launch {
            _uiState.value = DetailTanamanUiState.Loading
            try {
                val tanaman = tanamanRepository.getTanamanById(idTanaman)
                _uiState.value = DetailTanamanUiState.Success(tanaman)
            } catch (e: IOException) {
                e.printStackTrace()
                _uiState.value = DetailTanamanUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                _uiState.value = DetailTanamanUiState.Error
            }
        }
    }
}