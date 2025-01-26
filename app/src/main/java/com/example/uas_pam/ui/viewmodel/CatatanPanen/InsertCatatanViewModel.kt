package com.example.uas_pam.ui.viewmodel.CatatanPanen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.Model.CatatanPanen
import com.example.uas_pam.Repository.CatatanRepository
import kotlinx.coroutines.launch

class InsertCatatanViewModel(private val catatan: CatatanRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertCatatanUiState())
        private set

    fun updateInsertCatatanState(insertUiEvent: InsertCatatanUiEvent) {
        uiState = InsertCatatanUiState(insertCatatanUiEvent = insertUiEvent)
    }

    fun insertCatatan() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                catatan.insertCatatan(uiState.insertCatatanUiEvent.toCatatan())
                uiState = uiState.copy(isSuccess = true, isLoading = false) // Update state success
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false) // Handle error
                e.printStackTrace()
            }
        }
    }
}

data class InsertCatatanUiState(
    val insertCatatanUiEvent: InsertCatatanUiEvent = InsertCatatanUiEvent(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)

data class InsertCatatanUiEvent(
    val idPanen: String = "",
    val idTanaman: String = "",
    val tanggalPanen: String = "",
    val jumlahPanen: String = "",
    val keterangan: String = "",
) {
}

fun InsertCatatanUiEvent.toCatatan(): CatatanPanen = CatatanPanen(
    idPanen = idPanen,
    idTanaman = idTanaman,
    tanggalPanen = tanggalPanen,
    jumlahPanen = jumlahPanen,
    keterangan = keterangan,
)
