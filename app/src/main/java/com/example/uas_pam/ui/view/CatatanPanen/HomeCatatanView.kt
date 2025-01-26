package com.example.uas_pam.ui.view.CatatanPanen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.Model.CatatanPanen
import com.example.uas_pam.ui.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.viewmodel.CatatanPanen.CatatanPenyediaViewModel
import com.example.uas_pam.ui.viewmodel.CatatanPanen.HomeCatatanUiState
import com.example.uas_pam.ui.viewmodel.CatatanPanen.HomeCatatanViewModel

object DestinasiCatatanHome : DestinasiNavigasi {
    override val route = "catatan_home"
    override val titleRes = "home_catatan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenCatatan(
    navigateToCatatanEntry: () -> Unit,
    navigateToSplash: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (CatatanPanen) -> Unit = {},
    onRefresh: () -> Unit = {},
    viewModel: HomeCatatanViewModel = viewModel(factory = CatatanPenyediaViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.getCatatan()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            SmallTopAppBar(
                title = { Text("Daftar Catatan") },
                navigationIcon = {
                    IconButton(onClick = navigateToSplash) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.getCatatan() }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToCatatanEntry) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Catatan")
            }
        }
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.catatanUiState,
            retryAction = { viewModel.getCatatan() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick, // Mengirimkan Tanaman
            onDeleteClick = { catatan -> viewModel.deletecatatan(catatan.idPanen)}
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeCatatanUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (CatatanPanen) -> Unit, // Mengubah menjadi Tanaman
    onDeleteClick: (CatatanPanen) -> Unit
) {
    when (homeUiState) {
        is HomeCatatanUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is HomeCatatanUiState.Success -> {
            if (homeUiState.catatan.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Tidak ada data catatan panen")
                }
            } else {
                CatatanLayout(
                    catatan = homeUiState.catatan,
                    modifier = modifier,
                    onDetailClick = onDetailClick, // Mengirimkan Tanaman
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is HomeCatatanUiState.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Error loading data")
                    Button(onClick = retryAction) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}




// Gunakan TanamanLayout untuk menampilkan data tanaman
@Composable
fun CatatanLayout(
    catatan: List<CatatanPanen>,
    modifier: Modifier = Modifier,
    onDetailClick: (CatatanPanen) -> Unit, // Mengubah parameter menjadi Tanaman
    onDeleteClick: (CatatanPanen) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(catatan) { catatan ->
            CatatanCard(
                catatan = catatan,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(catatan) } // Mengirim seluruh objek tanaman
                    .padding(8.dp),
                onDeleteClick = { onDeleteClick(catatan) }
            )
        }
    }
}

@Composable
fun CatatanCard(
    catatan: CatatanPanen,
    modifier: Modifier = Modifier,
    onDeleteClick: (CatatanPanen) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Catatan Icon",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = catatan.idPanen,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(catatan) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Catatan Panen",
                    )
                }
            }

            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))

            // Baris kedua: Semua data ditampilkan horizontal
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("ID Panen:", style = MaterialTheme.typography.bodyMedium)
                Text(catatan.idPanen, style = MaterialTheme.typography.bodyMedium)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("ID Tanaman:", style = MaterialTheme.typography.bodyMedium)
                Text(catatan.idTanaman, style = MaterialTheme.typography.bodyMedium)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Tanggal Panen:", style = MaterialTheme.typography.bodyMedium)
                Text(catatan.tanggalPanen, style = MaterialTheme.typography.bodyMedium)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Jumlah Panen:", style = MaterialTheme.typography.bodyMedium)
                Text(catatan.jumlahPanen, style = MaterialTheme.typography.bodyMedium)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Keterangan:", style = MaterialTheme.typography.bodyMedium)
                Text(catatan.jumlahPanen, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}