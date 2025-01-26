package com.pam.uas_pam.view.tanaman

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.Model.Tanaman
import com.example.uas_pam.ui.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.viewmodel.Tanaman.TanamanPenyediaViewModel
import com.pam.uas_pam.viewmodel.tanaman.HomeTanamanViewModel
import com.pam.uas_pam.viewmodel.tanaman.HomeUiState

object DestinasiTanamanHome : DestinasiNavigasi {
    override val route = "home_tanaman"
    override val titleRes = "list_tanaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTanaman(
    navigateToItemEntry: () -> Unit,
    navigateToSplash: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Tanaman) -> Unit = {},
    onRefresh: () -> Unit = {},
    viewModel: HomeTanamanViewModel = viewModel(factory = TanamanPenyediaViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.getTanaman()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            SmallTopAppBar(
                title = { Text("Daftar Tanaman") },
                navigationIcon = {
                    IconButton(onClick = navigateToSplash) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.getTanaman() }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToItemEntry) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Tanaman")
            }
        }
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.tanamanUiState,
            retryAction = { viewModel.getTanaman() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick, // Mengirimkan Tanaman
            onDeleteClick = { tanaman -> viewModel.deleteTanaman(tanaman.idTanaman)}
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Tanaman) -> Unit, // Mengubah menjadi Tanaman
    onDeleteClick: (Tanaman) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is HomeUiState.Success -> {
            if (homeUiState.tanaman.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Tidak ada data tanaman")
                }
            } else {
                TanamanLayout(
                    tanaman = homeUiState.tanaman,
                    modifier = modifier,
                    onDetailClick = onDetailClick, // Mengirimkan Tanaman
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is HomeUiState.Error -> {
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
    tanaman: List<Tanaman>,
    modifier: Modifier = Modifier,
    onDetailClick: (Tanaman) -> Unit, // Mengubah parameter menjadi Tanaman
    onDeleteClick: (Tanaman) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(tanaman) { tanaman ->
            TanamanCard(
                tanaman = tanaman,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(tanaman) } // Mengirim seluruh objek tanaman
                    .padding(8.dp),
                onDeleteClick = { onDeleteClick(tanaman) }
            )
        }
    }
}

@Composable
fun TanamanCard(
    tanaman: Tanaman,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tanaman) -> Unit = {}
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
                    contentDescription = "Tanaman Icon",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = tanaman.idTanaman,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(tanaman) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Tanaman",
                    )
                }
            }

            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))

            // Baris kedua: Semua data ditampilkan horizontal
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("ID Tanaman:", style = MaterialTheme.typography.bodyMedium)
                Text(tanaman.idTanaman, style = MaterialTheme.typography.bodyMedium)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Nama Tanaman:", style = MaterialTheme.typography.bodyMedium)
                Text(tanaman.namaTanaman, style = MaterialTheme.typography.bodyMedium)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Periode Tanam:", style = MaterialTheme.typography.bodyMedium)
                Text(tanaman.periodeTanaman, style = MaterialTheme.typography.bodyMedium)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Deskripsi tanaman:", style = MaterialTheme.typography.bodyMedium)
                Text(tanaman.deskripsiTanaman, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}