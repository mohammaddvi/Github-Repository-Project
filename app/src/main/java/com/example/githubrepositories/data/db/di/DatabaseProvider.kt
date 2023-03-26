package com.example.githubrepositories.data.db.di

import android.content.Context
import androidx.room.Room
import com.example.githubrepositories.common.DATABASE_NAME
import com.example.githubrepositories.data.db.ProjectsDatabase
import com.example.githubrepositories.data.db.dao.ProjectDao
import com.example.githubrepositories.data.db.dao.RemoteKeysDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val projectDBModule = module {
    fun provideDataBase(context: Context): ProjectsDatabase =
        Room.databaseBuilder(
            context,
            ProjectsDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration ()
            .build()

    fun provideDao(dataBase: ProjectsDatabase): ProjectDao = dataBase.projectDao()
    fun provideRemoteKeysDao(dataBase: ProjectsDatabase): RemoteKeysDao = dataBase.remoteKeysDao()

    single { provideDataBase(androidContext()) }
    single { provideDao(get()) }
    single { provideRemoteKeysDao(get()) }
}
