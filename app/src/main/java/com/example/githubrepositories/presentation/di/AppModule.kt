package com.example.githubrepositories.presentation.di

import com.example.githubrepositories.presentation.ProjectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { ProjectViewModel(get()) }
}
