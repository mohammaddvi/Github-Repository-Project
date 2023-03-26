package com.example.githubrepositories.data.network.di

import android.util.Log
import com.example.githubrepositories.common.PROJECTS_REPO_BASE_URL
import com.example.githubrepositories.data.network.ApiClient
import com.example.githubrepositories.data.network.retrofit.RetrofitApi
import com.example.githubrepositories.data.network.retrofit.ApiClientImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val networkModule = module {
    single { provideRetrofit(get()) }
    single<ApiClient> { ApiClientImpl(get()) }
    single<RetrofitApi> {
        provideRetrofit(get()).create()
    }
    single { provideOkHttpClient() }
}

var gson: Gson = GsonBuilder()
    .setLenient()
    .create()

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder().baseUrl(PROJECTS_REPO_BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create(
        gson
    )).build()
fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor { chain ->
        val request: Request = chain.request()
        Log.d("OkHttp Request", request.toString())
        val response = chain.proceed(request)
        Log.d("OkHttp Response", response.toString())
        response
    }.build()
}
