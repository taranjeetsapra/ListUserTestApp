package com.example.listusertestapp.presentation.sealed

/**
 * Created by Taranjeet Singh on 15/10/25.
 */
sealed interface UserIntent {
    object FetchUser : UserIntent
    object RetryFetch : UserIntent
}
