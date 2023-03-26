package com.example.githubrepositories

import android.app.Application
import com.example.githubrepositories.data.datasource.di.datasourceModule
import com.example.githubrepositories.data.db.di.projectDBModule
import com.example.githubrepositories.data.network.di.networkModule
import com.example.githubrepositories.data.repository.di.repositoryModule
import com.example.githubrepositories.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(
                listOf(
                    projectDBModule,
                    networkModule,
                    repositoryModule,
                    presentationModule,
                    datasourceModule
                )
            )
        }
    }
}