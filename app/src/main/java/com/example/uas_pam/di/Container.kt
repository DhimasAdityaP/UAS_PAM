package com.example.uas_pam.di

import com.example.uas_pam.Repository.AktivitasRepository
import com.example.uas_pam.Repository.CatatanRepository
import com.example.uas_pam.Repository.NetworkAktivitasRepository
import com.example.uas_pam.Repository.NetworkCatatanRepository
import com.example.uas_pam.Repository.NetworkPekerjaRepository
import com.example.uas_pam.Repository.NetworkTanamanRepository
import com.example.uas_pam.Repository.PekerjaRepository
import com.example.uas_pam.Repository.TanamanRepository
import com.example.uas_pam.Service.AktivitasService
import com.example.uas_pam.Service.CatatanService
import com.example.uas_pam.Service.PekerjaService
import com.example.uas_pam.Service.TanamanService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val tanamanRepository: TanamanRepository
    val pekerjaRepository: PekerjaRepository
    val catatanRepository: CatatanRepository
    val aktivitasRepository: AktivitasRepository
}

class Container : AppContainer {
    private val baseUrl = "http://10.0.2.2/pertaniana10/" // Pastikan base URL sesuai dengan folder struktur API
    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val tanamanService : TanamanService by lazy {
        retrofit.create(TanamanService::class.java)
    }

    override val tanamanRepository: TanamanRepository by lazy {
        NetworkTanamanRepository(tanamanService)
    }

    private val pekerjaService: PekerjaService by lazy {
        retrofit.create(PekerjaService::class.java)
    }

    override val pekerjaRepository: PekerjaRepository by lazy {
        NetworkPekerjaRepository(pekerjaService)
    }

    private val catatanService: CatatanService by lazy {
        retrofit.create(CatatanService::class.java)
    }

    override val catatanRepository: CatatanRepository by lazy {
        NetworkCatatanRepository(catatanService)
    }

    private val aktivitasService: AktivitasService by lazy {
        retrofit.create(AktivitasService::class.java)
    }

    override val aktivitasRepository: AktivitasRepository by lazy {
        NetworkAktivitasRepository(aktivitasService)
    }
}