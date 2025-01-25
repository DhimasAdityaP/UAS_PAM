package com.example.uas_pam.Repository


import com.example.uas_pam.Model.AktivitasPertanian
import com.example.uas_pam.Model.Tanaman
import com.example.uas_pam.Service.AktivitasService
import okio.IOException

interface AktivitasRepository {
    suspend fun getAktivitas(): List<AktivitasPertanian>
    suspend fun insertAktivitas(aktivitasPertanian: AktivitasPertanian)
    suspend fun updateAktivitas(id_aktivitas: String, aktivitasPertanian: AktivitasPertanian)
    suspend fun deleteAktivitas(id_aktivitas: String)
    suspend fun getAktivitasById(id_aktivitas: String): AktivitasPertanian
}

class NetworkAktivitasRepository(
    private val AktivitasAPIService: AktivitasService
) : AktivitasRepository {

    override suspend fun getAktivitas(): List<AktivitasPertanian> {
        try {
            return AktivitasAPIService.getaktivitas()
        } catch (e: IOException) {
            throw IOException("Failed to fetch aktivitas Pertanian list. Network error occurred.", e)
        }
    }

    override suspend fun getAktivitasById(id_aktivitas: String): AktivitasPertanian {
        try {
            return AktivitasAPIService.getAktivitasById(id_aktivitas)
        } catch (e: IOException) {
            throw IOException("Failed to fetch Aktivitas Pertanian with id_aktivitas: $id_aktivitas. Network error occurred.", e)
        }
    }

    override suspend fun insertAktivitas(aktivitasPertanian: AktivitasPertanian) {
        try {
            val response = AktivitasAPIService.insertAktivitas(aktivitasPertanian)
            if (!response.isSuccessful) {
                throw IOException("Failed to insert aktivitas pertanian. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to insert aktivitas pertanian. Network error occurred.", e)
        }
    }

    override suspend fun updateAktivitas(id_aktivitas: String, aktivitasPertanian: AktivitasPertanian) {
        try {
            val response = AktivitasAPIService.updateaktivitas(id_aktivitas,aktivitasPertanian)
            if (!response.isSuccessful) {
                throw IOException("Failed to update aktivitas pertanian with id_aktivitas: $id_aktivitas. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to update aktivitas pertanian. Network error occurred.", e)
        }
    }

    override suspend fun deleteAktivitas(id_aktivitas: String) {
        try {
            val response = AktivitasAPIService.deleteaktivitas(id_aktivitas)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete aktivitas pertanian with id_aktivitas: $id_aktivitas. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to delete aktivitas pertanian. Network error occurred.", e)
        }
    }
}