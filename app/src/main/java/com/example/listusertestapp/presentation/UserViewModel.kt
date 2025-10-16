package com.example.listusertestapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listusertestapp.data.model.UserInfo
import com.example.listusertestapp.data.service.api.UserInfoRepo
import com.example.listusertestapp.domain.repository.ResultState
import com.example.listusertestapp.presentation.sealed.ResourceState
import com.example.listusertestapp.presentation.sealed.UserIntent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Created by Taranjeet Singh on 16/10/25.
 */
class UserViewModel(val userInfoRepo: UserInfoRepo) : ViewModel() {

    // State (MVI: Model/State)
    private val _state = MutableStateFlow<ResourceState<List<UserInfo>>>(ResourceState.Loading)
    val state: StateFlow<ResourceState<List<UserInfo>>> = _state.asStateFlow()


    private val _userIntent = Channel<UserIntent>(Channel.UNLIMITED)
    val effect = _userIntent
   // val ii = _userIntent.receiveAsFlow()


    // Initial load
    init {
        processIntent()
        sendIntent()
    }

    fun sendIntent(){
        viewModelScope.launch {
            effect.send(UserIntent.FetchUser)
        }
    }

    // Process Intents (MVI: Intent)
    fun processIntent() {
        viewModelScope.launch {

            effect.consumeAsFlow().collect { collector: UserIntent->
                when(collector){
                    is UserIntent.FetchUser ->{
                        delay(10_000L)
                        fetchUsers()
                    }

                    UserIntent.RetryFetch -> {

                    }
                }
            }
        }

    }


    fun fetchUsers() {
        _state.value = ResourceState.Loading // Set to Loading state

        viewModelScope.launch {
            val result: ResultState<List<UserInfo>> = userInfoRepo.getUserInfo()

            when (result) {
                is ResultState.Failure -> {
                    result.exception.message?.let {
                        _state.value = ResourceState.Error(it)
                    }

                }

                is ResultState.Success<List<UserInfo>> -> {
                    _state.value = ResourceState.Success(result.value)
                }
            }

        }
    }

}