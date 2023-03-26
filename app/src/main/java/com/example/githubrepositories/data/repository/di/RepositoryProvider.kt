package com.example.githubrepositories.data.repository.di

import com.example.githubrepositories.data.repository.ProjectRepository
import com.example.githubrepositories.data.repository.ProjectRepositoryImpl
import com.example.githubrepositories.data.repository.paging.ProjectPagingSource
import com.example.githubrepositories.data.repository.paging.ProjectRemoteMediator
import org.koin.dsl.module

val repositoryModule = module {
    factory<ProjectRepository> { ProjectRepositoryImpl(get(), get(), get(), get()) }
    factory { ProjectPagingSource(get(), get()) }
    factory { ProjectRemoteMediator(get(), get(), get()) }
}