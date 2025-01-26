package com.example.uas_pam.ui.viewmodel.Pekerja


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.Model.Pekerja
import com.example.uas_pam.Repository.PekerjaRepository
import kotlinx.coroutines.launch

class InsertPekerjaViewModel(private val pekerja: PekerjaRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertPekerjaState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    fun insertPekerja() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                pekerja.insertPekerja(uiState.insertUiEvent.toPekerja())
                uiState = uiState.copy(isSuccess = true, isLoading = false) // Update state success
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false) // Handle error
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)

data class InsertUiEvent(
    val idPekerja: String = "",
    val namaPekerja: String = "",
    val jabatan: String = "",
    val kontakPekerja: String = ""
)

fun InsertUiEvent.toPekerja(): Pekerja = Pekerja(
    idPekerja = idPekerja,
    namaPekerja = namaPekerja,
    jabatan = jabatan,
    kontakPekerja = kontakPekerja
)

fun Pekerja.toUiStatePekerja(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Pekerja.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idPekerja = idPekerja,
    namaPekerja = namaPekerja,
    jabatan = jabatan,
    kontakPekerja = kontakPekerja
)
