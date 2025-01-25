package com.example.uas_pam.Repository


import com.example.uas_pam.Model.CatatanPanen
import com.example.uas_pam.Model.Tanaman
import com.example.uas_pam.Service.CatatanService
import okio.IOException

interface CatatanRepository {
    suspend fun getCatatan(): List<CatatanPanen>
    suspend fun insertCatatan(catatanPanen: CatatanPanen)
    suspend fun updateCatatan(id_panen: String, catatanPanen: CatatanPanen)
    suspend fun deleteCatatan(id_panen: String)
    suspend fun getCatatanById(id_panen: String): CatatanPanen
}

class NetworkCatatanRepository(
    private val CatatanAPIService: CatatanService
) : CatatanRepository {

    override suspend fun getCatatan(): List<CatatanPanen> {
        try {
            return CatatanAPIService.getcatatan()
        } catch (e: IOException) {
            throw IOException("Failed to fetch catatan list. Network error occurred.", e)
        }
    }

    override suspend fun getCatatanById(id_panen: String): CatatanPanen {
        try {
            return CatatanAPIService.getCatatanById(id_panen)
        } catch (e: IOException) {
            throw IOException("Failed to fetch catatan with id_panen: $id_panen. Network error occurred.", e)
        }
    }

    override suspend fun insertCatatan(catatanPanen: CatatanPanen) {
        try {
            val response = CatatanAPIService.insertcatatan(catatanPanen)
            if (!response.isSuccessful) {
                throw IOException("Failed to insert catatan panen. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to insert catatan panen. Network error occurred.", e)
        }
    }

    override suspend fun updateCatatan(id_panen: String, catatanPanen: CatatanPanen) {
        try {
            val response = CatatanAPIService.updatecatatan(id_panen,catatanPanen)
            if (!response.isSuccessful) {
                throw IOException("Failed to update catatan  with id_tanaman: $id_panen. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to update catatan. Network error occurred.", e)
        }
    }

    override suspend fun deleteCatatan(id_panen: String) {
        try {
            val response = CatatanAPIService.deletecatatan(id_panen)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete catatan pekerja with id_panen: $id_panen. HTTP Status code: ${response.code()}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to delete catatan pekerja. Network error occurred.", e)
        }
    }
}