package com.example.uas_pam.ui.view.CatatanPanen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.ui.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.viewmodel.CatatanPanen.CatatanPenyediaViewModel
import com.example.uas_pam.ui.viewmodel.CatatanPanen.InsertCatatanUiEvent
import com.example.uas_pam.ui.viewmodel.CatatanPanen.InsertCatatanViewModel
import kotlinx.coroutines.launch

object DestinasiInsertCatatan : DestinasiNavigasi {
    override val route = "catatan_entry"
    override val titleRes = "Tambah Catatan"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryCatatanScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertCatatanViewModel = viewModel(factory = CatatanPenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState = viewModel.uiState

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SmallTopAppBar(
                title = { Text("Masukan Data Catatan Panen") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        EntryBodyCatatan(
            insertCatatanUiEvent = uiState.insertCatatanUiEvent,
            isLoading = uiState.isLoading,
            onCatatanValueChange = { field, value ->
                viewModel.updateInsertCatatanState(
                    uiState.insertCatatanUiEvent.copy(
                        idPanen = if (field == "idPanen") value else uiState.insertCatatanUiEvent.idPanen,
                        idTanaman = if (field == "idTanaman") value else uiState.insertCatatanUiEvent.idTanaman,
                        tanggalPanen = if (field == "tanggalPanen") value else uiState.insertCatatanUiEvent.tanggalPanen,
                        jumlahPanen = if (field == "jumlahPanen") value else uiState.insertCatatanUiEvent.jumlahPanen,
                        keterangan = if (field == "keterangan") value else uiState.insertCatatanUiEvent.keterangan
                    )
                )
            },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertCatatan()
                    if (viewModel.uiState.isSuccess) {
                        navigateBack()
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyCatatan(
    insertCatatanUiEvent: InsertCatatanUiEvent,
    isLoading: Boolean,
    onCatatanValueChange: (field: String, value: String) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputCatatan(
            insertCatatanUiEvent = insertCatatanUiEvent,
            onValueChange = onCatatanValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            enabled = insertCatatanUiEvent.isValid()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text(text = "Simpan")
            }
        }
    }
}

@Composable
fun FormInputCatatan(
    insertCatatanUiEvent: InsertCatatanUiEvent,
    onValueChange: (field: String, value: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertCatatanUiEvent.idPanen,
            onValueChange = { onValueChange("idPanen", it) },
            label = { Text("ID Panen") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = insertCatatanUiEvent.idTanaman,
            onValueChange = { onValueChange("idTanaman", it) },
            label = { Text("ID Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = insertCatatanUiEvent.tanggalPanen,
            onValueChange = { onValueChange("tanggalPanen", it) },
            label = { Text("Tanggal Panen") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = insertCatatanUiEvent.jumlahPanen,
            onValueChange = { onValueChange("jumlahPanen", it) },
            label = { Text("Jumlah Panen") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = insertCatatanUiEvent.keterangan,
            onValueChange = { onValueChange("keterangan", it) },
            label = { Text("Keterangan") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}

fun InsertCatatanUiEvent.isValid(): Boolean {
    return idPanen.isNotEmpty() &&
            idTanaman.isNotEmpty() &&
            tanggalPanen.isNotEmpty() &&
            jumlahPanen.isNotEmpty() &&
            keterangan.isNotEmpty()
}
