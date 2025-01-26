package com.example.uas_pam.ui.view.Pekerja

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
import com.example.uas_pam.ui.viewmodel.Pekerja.InsertPekerjaViewModel
import com.example.uas_pam.ui.viewmodel.Pekerja.InsertUiEvent
import com.example.uas_pam.ui.viewmodel.Pekerja.InsertUiState
import com.example.uas_pam.ui.viewmodel.Pekerja.PekerjaPenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertPekerja : DestinasiNavigasi {
    override val route = "pekerja_entry"
    override val titleRes = "Tambah Pekerja"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPekerjaScreen(
    navigateBack: () -> Unit, // Navigasi kembali
    modifier: Modifier = Modifier,
    viewModel: InsertPekerjaViewModel = viewModel(factory = PekerjaPenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SmallTopAppBar(
                title = { Text("Masukan Data Pekerja") },
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
        EntryBodyPekerja(
            insertUiState = viewModel.uiState,
            onPekerjaValueChange = { field, value ->
                viewModel.updateInsertPekerjaState(
                    viewModel.uiState.insertUiEvent.copy(
                        idPekerja = if (field == "idPekerja") value else viewModel.uiState.insertUiEvent.idPekerja,
                        namaPekerja = if (field == "namaPekerja") value else viewModel.uiState.insertUiEvent.namaPekerja,
                        jabatan = if (field == "jabatan") value else viewModel.uiState.insertUiEvent.jabatan,
                        kontakPekerja = if (field == "kontakPekerja") value else viewModel.uiState.insertUiEvent.kontakPekerja
                    )
                )
            },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPekerja()
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
fun EntryBodyPekerja(
    insertUiState: InsertUiState,
    onPekerjaValueChange: (field: String, value: String) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPekerja(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onPekerjaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            enabled = insertUiState.insertUiEvent.idPekerja.isNotEmpty() &&
                    insertUiState.insertUiEvent.namaPekerja.isNotEmpty() &&
                    insertUiState.insertUiEvent.jabatan.isNotEmpty() &&
                    insertUiState.insertUiEvent.kontakPekerja.isNotEmpty()
        ) {
            if (insertUiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text(text = "Simpan")
            }
        }
    }
}

@Composable
fun FormInputPekerja(
    insertUiEvent: InsertUiEvent,
    onValueChange: (field: String, value: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.idPekerja,
            onValueChange = { onValueChange("idPekerja", it) },
            label = { Text("ID Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.namaPekerja,
            onValueChange = { onValueChange("namaPekerja", it) },
            label = { Text("Nama Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.jabatan,
            onValueChange = { onValueChange("jabatan", it) },
            label = { Text("Jabatan") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.kontakPekerja,
            onValueChange = { onValueChange("kontakPekerja", it) },
            label = { Text("Kontak Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}
