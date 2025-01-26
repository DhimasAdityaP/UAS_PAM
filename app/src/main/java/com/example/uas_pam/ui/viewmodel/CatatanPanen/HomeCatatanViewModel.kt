package com.example.uas_pam.ui.viewmodel.CatatanPanen


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uas_pam.Model.CatatanPanen
import com.example.uas_pam.Repository.CatatanRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeCatatanUiState {
    data class Success(val catatan : List<CatatanPanen>) : HomeCatatanUiState()
    data class Error(val message: String) : HomeCatatanUiState() // Add error message
    object Loading : HomeCatatanUiState()
}

class HomeCatatanViewModel(private val catatanRepository: CatatanRepository) : ViewModel() {

    var catatanUiState: HomeCatatanUiState by mutableStateOf(HomeCatatanUiState.Loading)
        private set

    init {
        getCatatan()
    }

    // Function to get plant data
    fun getCatatan() {
        viewModelScope.launch {
            safeApiCall(
                call = {
                    val catatanList = catatanRepository.getCatatan()
                    catatanUiState = HomeCatatanUiState.Success(catatanList)
                },
                onError = { errorMessage ->
                    catatanUiState = HomeCatatanUiState.Error(errorMessage)
                }
            )
        }
    }

    // Function to delete plant by ID
    fun deletecatatan(idpanen: String) {
        viewModelScope.launch {
            safeApiCall(
                call = {
                    catatanRepository.deleteCatatan(idpanen)
                    getCatatan() // Refresh data after deletion
                },
                onError = { errorMessage ->
                    catatanUiState = HomeCatatanUiState.Error(errorMessage)
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
