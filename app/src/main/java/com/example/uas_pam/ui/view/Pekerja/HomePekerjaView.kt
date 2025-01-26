package com.example.uas_pam.ui.view.Pekerja

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.Model.Pekerja
import com.example.uas_pam.Model.Tanaman
import com.example.uas_pam.ui.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.viewmodel.Pekerja.HomePekerjaUiState
import com.example.uas_pam.ui.viewmodel.Pekerja.HomePekerjaViewModel
import com.example.uas_pam.ui.viewmodel.Pekerja.PekerjaPenyediaViewModel

object DestinasiHomePekerja : DestinasiNavigasi {
    override val route = "home_pekerja"
    override val titleRes = "list_pekerja"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenPekerja(
    navigateToPekerjaEntry: () -> Unit,
    navigateToSplash: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Pekerja) -> Unit = {},
    onRefresh: () -> Unit = {},
    viewModel: HomePekerjaViewModel = viewModel(factory =PekerjaPenyediaViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.getPekerja()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            SmallTopAppBar(
                title = { Text("Daftar Pekerja") },
                navigationIcon = {
                    IconButton(onClick = navigateToSplash) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.getPekerja() }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToPekerjaEntry) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Pekerja")
            }
        }
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.pekerjaUiState,
            retryAction = { viewModel.getPekerja() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick, // Mengirimkan Tanaman
            onDeleteClick = { Pekerja -> viewModel.deletepekerja(Pekerja.idPekerja)}
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomePekerjaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Pekerja) -> Unit, // Mengubah menjadi Tanaman
    onDeleteClick: (Pekerja) -> Unit
) {
    when (homeUiState) {
        is HomePekerjaUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is HomePekerjaUiState.Success -> {
            if (homeUiState.pekerja.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Tidak ada data pekerja")
                }
            } else {
                TanamanLayout(
                    pekerja = homeUiState.pekerja,
                    modifier = modifier,
                    onDetailClick = onDetailClick, // Mengirimkan Tanaman
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is HomePekerjaUiState.Error -> {
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
fun TanamanLayout(
    pekerja: List<Pekerja>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pekerja) -> Unit, // Mengubah parameter menjadi Tanaman
    onDeleteClick: (Pekerja) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(pekerja) { pekerja ->
            TanamanCard(
                pekerja = pekerja,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(pekerja) } // Mengirim seluruh objek tanaman
                    .padding(8.dp),
                onDeleteClick = { onDeleteClick(pekerja) }
            )
        }
    }
}

@Composable
fun TanamanCard(
    pekerja: Pekerja,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pekerja) -> Unit = {}
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
                    contentDescription = "Pekerja Icon",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = pekerja.idPekerja,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(pekerja) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus pekerja",
                    )
                }
            }

            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))

            // Baris kedua: Semua data ditampilkan horizontal
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("ID pekerja:", style = MaterialTheme.typography.bodyMedium)
                Text(pekerja.idPekerja, style = MaterialTheme.typography.bodyMedium)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Nama pekerja:", style = MaterialTheme.typography.bodyMedium)
                Text(pekerja.namaPekerja, style = MaterialTheme.typography.bodyMedium)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("jabatan:", style = MaterialTheme.typography.bodyMedium)
                Text(pekerja.jabatan, style = MaterialTheme.typography.bodyMedium)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("kontak pekerja:", style = MaterialTheme.typography.bodyMedium)
                Text(pekerja.kontakPekerja, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}