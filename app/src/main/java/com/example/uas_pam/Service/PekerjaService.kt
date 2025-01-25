package com.example.uas_pam.Service

import com.example.uas_pam.Model.Pekerja
import retrofit2.Response
import retrofit2.http.*

interface PekerjaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("get_pekerja.php")
    suspend fun getPekerja(): List<Pekerja>

    @GET("baca1pekerja.php")
    suspend fun getPekerjaById(@Query("id_pekerja") id_pekerja: String): Pekerja

    @POST("add_pekerja.php")
    suspend fun insertPekerja(@Body pekerja: Pekerja): Response<Void>

    @PUT("edit_pekerja.php")
    suspend fun updatepekerja(
        @Query("id_pekerja") id_pekerja: String,
        @Body pekerja: Pekerja
    ): Response<Void>

    @DELETE("delete_pekerja.php")
    suspend fun deletepekerja(@Query("id_pekerja") id_pekerja: String): Response<Void>
}