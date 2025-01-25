package com.example.uas_pam.Service

import com.example.uas_pam.Model.CatatanPanen
import retrofit2.Response
import retrofit2.http.*

interface CatatanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("get_catatan.php")
    suspend fun getcatatan(): List<CatatanPanen>

    @GET("baca1catatan.php")
    suspend fun getCatatanById(@Query("id_panen") id_panen: String): CatatanPanen

    @POST("add_catatan.php")
    suspend fun insertcatatan(@Body catatanPanen: CatatanPanen): Response<Void>

    @PUT("edit_catatan.php")
    suspend fun updatecatatan(
        @Query("id_panen") id_panen: String,
        @Body catatanPanen: CatatanPanen
    ): Response<Void>

    @DELETE("delete_catatan.php")
    suspend fun deletecatatan(@Query("id_panen") id_panen: String): Response<Void>
}