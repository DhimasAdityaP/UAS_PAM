package com.example.uas_pam.Model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pekerja(
    @SerialName("id_pekerja")
    val idPekerja: String,
    @SerialName("nama_pekerja")
    val namaPekerja: String,
    val jabatan: String,
    @SerialName("kontak_pekerja")
    val kontakPekerja: String
)
