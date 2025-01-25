package com.example.uas_pam.Repository

import com.example.uas_pam.Model.Tanaman
import com.example.uas_pam.Service.TanamanService
import okio.IOException

interface TanamanRepository {
    suspend fun getTanaman(): List<Tanaman>
    suspend fun insertTanaman(tanaman: Tanaman)
    suspend fun updateTanaman(idTanaman: String, tanaman: Tanaman)
    suspend fun deleteTanaman(idTanaman: String)
    suspend fun getTanamanById(idTanaman: String): Tanaman
}

class NetworkTanamanRepository(
    private val TanamanAPIService: TanamanService
) : TanamanRepository {

    override suspend fun getTanaman(): List<Tanaman> {
        try {
            return TanamanAPIService.getTanaman()
        } catch (e: IOException) {
            throw IOException("Failed to fetch mahasiswa list. Network error occurred.", e)
        }
    }

    override suspend fun getTanamanById(idTanaman: String): Tanaman {
        try {
            return TanamanAPIService.getTanamanById(idTanaman)
        } catch (e: IOException) {
            throw IOException("Failed to fetch tanaman with idTanaman: $idTanaman. Network error occurred.", e)
        }
    }

    override suspend fun insertTanaman(tanaman: Tanaman) {
        try {
            val response = TanamanAPIService.insertTanaman(tanaman)
            if (!response.isSuccessful) {
                throw IOException("Failed to insert tanaman. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to insert tanaman. Network error occurred.", e)
        }
    }

    override suspend fun updateTanaman(idTanaman: String, tanaman: Tanaman) {
        try {
            val response = TanamanAPIService.updateTanaman(idTanaman,tanaman)
            if (!response.isSuccessful) {
                throw IOException("Failed to update tanaman with idTanaman: $idTanaman. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to update tanaman. Network error occurred.", e)
        }
    }

    override suspend fun deleteTanaman(idTanaman: String) {
        try {
            val response = TanamanAPIService.deleteTanaman(idTanaman)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete tanaman with idTanaman: $idTanaman. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to delete tanaman. Network error occurred.", e)
        }
    }
}