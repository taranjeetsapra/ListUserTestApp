package com.example.listusertestapp.di

import com.example.listusertestapp.data.service.api.UserApi
import com.example.listusertestapp.data.service.api.UserInfoRepo
import com.example.listusertestapp.data.service.api.UserInfoService
import com.example.listusertestapp.presentation.UserViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Taranjeet Singh on 15/10/25.
 */
val appModule = module {
    // Singletons for services and repositories
   // single { UserService() }
    single { UserInfoRepo(get()) }

    // Factory for ViewModel
    viewModel { UserViewModel(get()) }
}

val networkModule = module {

    // 1. Gson/Converter Factory
    single<Gson> {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single<GsonConverterFactory> {
        GsonConverterFactory.create(get<Gson>())
    }

    // 2. OkHttpClient
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            // Add interceptors here if needed (e.g., logging, headers)
            // .addInterceptor(get<LoggingInterceptor>())
            .build()
    }

    // 3. Retrofit Instance (The requested function)
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(UserInfoService.BASE_URL) // ⬅️ Replace with your base URL
            .client(get<OkHttpClient>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }


     factory<UserApi> { get<Retrofit>().create(UserApi::class.java) }
}