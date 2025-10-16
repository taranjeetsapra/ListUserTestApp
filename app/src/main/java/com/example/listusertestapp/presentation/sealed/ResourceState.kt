package com.example.listusertestapp.presentation.sealed

/**
 * Created by Taranjeet Singh on 19/09/25.
 */
sealed class ResourceState<out T> {

    object Idle : ResourceState<Nothing>()
    object Loading : ResourceState<Nothing>()
    data class Success<out T>(val dataVal: T) : ResourceState<T>()
    data class Error(val msg: String) : ResourceState<Nothing>()

}