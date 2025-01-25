package com.example.uas_pam.ui.view.Tanaman

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.uas_pam.Model.Tanaman
import com.example.uas_pam.ui.viewmodel.Tanaman.DetailTanamanUiState
import com.example.uas_pam.ui.viewmodel.Tanaman.DetailTanamanViewModel

object DestinasiDetailTanaman {
    const val route = "tanaman_detail"
    const val titleRes = "Detail Tanaman"
}
@Composable
fun DetailTanamanView(
    navigateBack:() ->Unit,
    onClick:()->Unit,
    viewModel: DetailTanamanViewModel, idTanaman: String) {
    // Collecting the UI state from the ViewModel
    val uiState = viewModel.uiState.collectAsState().value

    // Handle different states of the UI
    when (uiState) {
        is DetailTanamanUiState.Loading -> {
            LoadingView()
        }
        is DetailTanamanUiState.Success -> {
            TanamanDetailView(tanaman = uiState.tanaman)
        }
        is DetailTanamanUiState.Error -> {
            ErrorView()
        }
    }

    // Fetch tanaman data by id
    viewModel.getTanamanById(idTanaman)
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun TanamanDetailView(tanaman: Tanaman) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "id Tanaman: ${tanaman.idTanaman}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Nama Tanaman: ${tanaman.namaTanaman}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Periode Tanam: ${tanaman.periodeTanaman}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Deskripsi Tanaman: ${tanaman.deskripsiTanaman}")
        Spacer(modifier = Modifier.height(8.dp))
        // Add more fields as necessary
    }
}

@Composable
fun ErrorView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Terjadi kesalahan. Coba lagi.")
    }
}