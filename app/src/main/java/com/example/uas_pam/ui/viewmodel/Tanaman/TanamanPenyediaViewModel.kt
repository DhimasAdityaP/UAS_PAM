package com.example.uas_pam.ui.viewmodel.Tanaman

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uas_pam.TanamanApplication
import com.pam.uas_pam.viewmodel.InsertTanamanViewModel
import com.pam.uas_pam.viewmodel.tanaman.HomeTanamanViewModel

object TanamanPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeTanamanViewModel(
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

    fun CreationExtras.aplikasiPertanian():TanamanApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TanamanApplication)

}