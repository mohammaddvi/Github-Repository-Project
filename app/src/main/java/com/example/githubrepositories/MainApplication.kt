package com.example.githubrepositories

import android.app.Application
import com.example.githubrepositories.di.appModule
import com.example.githubrepositories.di.projectDBModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(projectDBModule,appModule))
        }
    }
}