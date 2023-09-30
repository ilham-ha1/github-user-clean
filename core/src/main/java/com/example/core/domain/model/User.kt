package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id:Int,
    val username:String,
    val avatarUrl:String? = null,
): Parcelable