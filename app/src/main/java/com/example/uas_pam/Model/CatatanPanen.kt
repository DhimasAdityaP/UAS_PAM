package com.example.uas_pam.Model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatatanPanen(
    @SerialName("id_panen")
    val idPanen: String,
    @SerialName("id_tanaman")
    val idTanaman: String,
    @SerialName("tanggal_panen")
    val tanggalPanen: String,
    @SerialName("jumlah_panen")
    val jumlahPanen: String,
    val keterangan: String,
)
