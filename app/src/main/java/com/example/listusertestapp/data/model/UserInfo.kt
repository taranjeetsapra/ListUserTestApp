package com.example.listusertestapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val id: Int,
    val name: String,
    val company: String,
    val username: String,
    val email: String,
    val address: String,
    val zip: String,
    val state: String,
    val country: String,
    val phone: String,
    val photo: String // url for the image
): Parcelable