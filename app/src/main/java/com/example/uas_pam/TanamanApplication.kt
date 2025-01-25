package com.example.uas_pam


import android.app.Application
import com.example.uas_pam.di.AppContainer
import com.example.uas_pam.di.Container

class PertanianApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = Container()
    }
}