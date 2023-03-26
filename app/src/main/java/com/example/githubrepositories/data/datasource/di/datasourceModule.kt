package com.example.githubrepositories.data.datasource.di

import com.example.githubrepositories.data.datasource.LocalProjectDataSource
import com.example.githubrepositories.data.datasource.LocalProjectsDataSourceImpl
import com.example.githubrepositories.data.datasource.RemoteProjectDataSource
import com.example.githubrepositories.data.datasource.RemoteProjectsDataSourceImpl
import org.koin.dsl.module

val datasourceModule = module {
    factory<LocalProjectDataSource> { LocalProjectsDataSourceImpl(get()) }
    factory<RemoteProjectDataSource> { RemoteProjectsDataSourceImpl(get()) }
}