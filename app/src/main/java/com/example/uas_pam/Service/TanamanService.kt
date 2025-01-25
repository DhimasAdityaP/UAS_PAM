package com.example.uas_pam.Service

import com.example.uas_pam.Model.Tanaman
import retrofit2.Response
import retrofit2.http.*

interface TanamanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("gettanaman.php")
    suspend fun getTanaman(): List<Tanaman>

    @GET("baca1tanaman.php/{id_tanaman}")
    suspend fun getTanamanById(@Query("id_tanaman") idTanaman: String): Tanaman

    @POST("add_tanaman.php")
    suspend fun insertTanaman(@Body tanaman: Tanaman): Response<Void>

    @PUT("edit_tanaman.php")
    suspend fun updateTanaman(
        @Query("id_tanaman") idTanaman: String,
        @Body tanaman: Tanaman
    ): Response<Void>

    @DELETE("delete_mahasiswa.php")
    suspend fun deleteTanaman(@Query("id_tanaman") idTanaman: String): Response<Void>
}