package com.example.listusertestapp.data.service.api

import com.example.listusertestapp.data.model.UserInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Taranjeet Singh on 14/10/25.
 */
object UserInfoService {

    const val BASE_URL = "https://fake-json-api.mock.beeceptor.com/"

    private fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: UserApi = getRetrofit().create(UserApi::class.java)


}