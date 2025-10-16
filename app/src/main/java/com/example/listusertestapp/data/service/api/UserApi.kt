package com.example.listusertestapp.data.service.api

import com.example.listusertestapp.data.model.UserInfo
import com.example.listusertestapp.presentation.sealed.ResourceState
import retrofit2.http.GET

/**
 * Created by Taranjeet Singh on 14/10/25.
 */
interface UserApi {

    @GET("/users")
    suspend fun getUserInfo(): List<UserInfo>
}