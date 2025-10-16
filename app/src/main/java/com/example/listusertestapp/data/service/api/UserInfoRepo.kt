package com.example.listusertestapp.data.service.api

import com.example.listusertestapp.data.model.UserInfo
import com.example.listusertestapp.domain.repository.ResultState
import com.example.listusertestapp.presentation.sealed.ResourceState

/**
 * Created by Taranjeet Singh on 14/10/25.
 */
class UserInfoRepo(val userApi: UserApi) {

    suspend fun getUserInfo(): ResultState<List<UserInfo>> = try {
        val userinfo = userApi.getUserInfo()
        ResultState.Success(userinfo)
    } catch (e: Exception) {
        ResultState.Failure(e)
    }


}