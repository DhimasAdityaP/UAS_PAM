package com.pam.uas_pam.viewmodel.tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uas_pam.Model.Tanaman
import com.example.uas_pam.Repository.TanamanRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState {
    data class Success(val tanaman: List<Tanaman>) : HomeUiState()
    data class Error(val message: String) : HomeUiState() // Add error message
    object Loading : HomeUiState()
}

class HomeTanamanViewModel(private val tanamanRepository: TanamanRepository) : ViewModel() {

    var tanamanUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getTanaman()
    }

    // Function to get plant data
    fun getTanaman() {
        viewModelScope.launch {
            safeApiCall(
                call = {
                    val tanamanList = tanamanRepository.getTanaman()
                    tanamanUiState = HomeUiState.Success(tanamanList)
                },
                onError = { errorMessage ->
                    tanamanUiState = HomeUiState.Error(errorMessage)
                }
            )
        }
    }

    // Function to delete plant by ID
    fun deleteTanaman(idtanaman: String) {
        viewModelScope.launch {
            safeApiCall(
                call = {
                    tanamanRepository.deleteTanaman(idtanaman)
                    getTanaman() // Refresh data after deletion
                },
                onError = { errorMessage ->
                    tanamanUiState = HomeUiState.Error(errorMessage)
                }
            )
        }
    }

    // Function to handle errors consistently
    private suspend fun safeApiCall(call: suspend () -> Unit, onError: (String) -> Unit) {
        try {
            call()
        } catch (e: IOException) {
            onError("Network error: ${e.message}")
        } catch (e: HttpException) {
            onError("Server error: ${e.message}")
        } catch (e: Exception) {
            onError("Unexpected error: ${e.message}")
        }
    }
}
