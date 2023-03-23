package com.example.githubrepositories.di

import com.example.githubrepositories.common.PROJECTS_REPO_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(PROJECTS_REPO_BASE_URL)
        .client(okHttpClient).build()