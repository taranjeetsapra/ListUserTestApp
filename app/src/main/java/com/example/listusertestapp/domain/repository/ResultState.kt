package com.example.listusertestapp.domain.repository

/**
 * Created by Taranjeet Singh on 16/10/25.
 */

sealed class ResultState<out T> {

    // Represents a successful operation, holding the data of type T
    data class Success<out T>(val value: T) : ResultState<T>()

    // Represents a failed operation, holding the exception/throwable
    data class Failure(val exception: Throwable) : ResultState<Nothing>()

    // Note on 'Nothing': Failure doesn't depend on T, so using 'Nothing'
    // allows the entire sealed class to be covariant (T can be substituted
    // by a subtype), which is critical for flexible usage.
}