package com.example.githubrepositories.di

import android.content.Context
import androidx.room.Room
import com.example.githubrepositories.data.db.ProjectDao
import com.example.githubrepositories.data.db.ProjectsDatabase
import com.example.githubrepositories.data.repository.ProjectRepository
import com.example.githubrepositories.data.repository.ProjectRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val projectDBModule = module {
    fun provideDataBase(context: Context): ProjectsDatabase =
        Room.databaseBuilder(context, ProjectsDatabase::class.java, "project_db")
            .fallbackToDestructiveMigration()
            .build()

    fun provideDao(dataBase: ProjectsDatabase): ProjectDao = dataBase.projectDao()

    single { provideDataBase(androidContext()) }
    factory { provideDao(get()) }
}

val appModule = module {
    single { provideRetrofit(get()) }
    factory<ProjectRepository> { ProjectRepositoryImpl(get(),get()) }
}
