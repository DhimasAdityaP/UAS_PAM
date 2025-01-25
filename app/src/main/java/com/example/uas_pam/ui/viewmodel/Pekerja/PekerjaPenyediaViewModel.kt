package com.example.uas_pam.ui.viewmodel.Pekerja

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uas_pam.PertanianApplication


object PekerjaPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomePekerjaViewModel(
                aplikasiPertanian()
                    .container.tanamanRepository
            )
        }

        initializer {
            InsertTanamanViewModel(
                aplikasiPertanian()
                    .container.tanamanRepository
            )
        }
    }

    fun CreationExtras.aplikasiPertanian(): PertanianApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PertanianApplication)

}
