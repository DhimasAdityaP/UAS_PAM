package com.example.uas_pam.Service

import com.example.uas_pam.Model.AktivitasPertanian
import retrofit2.Response
import retrofit2.http.*

interface AktivitasService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("get_aktivitas.php")
    suspend fun getaktivitas(): List<AktivitasPertanian>

    @GET("baca1aktivitas.php")
    suspend fun getAktivitasById(@Query("id_aktivitas") id_aktivitas: String): AktivitasPertanian

    @POST("add_aktivitas.php")
    suspend fun insertAktivitas(@Body aktivitasPertanian: AktivitasPertanian): Response<Void>

    @PUT("edit_aktivitas.php")
    suspend fun updateaktivitas(
        @Query("id_aktivitas") id_aktivitas: String,
        @Body aktivitasPertanian: AktivitasPertanian
    ): Response<Void>

    @DELETE("delete_aktivitas.php")
    suspend fun deleteaktivitas(@Query("id_aktivitas") id_aktivitas: String): Response<Void>
}