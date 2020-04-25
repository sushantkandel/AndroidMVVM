package com.example.mvvmprac.data.network.response

import com.example.mvvmprac.data.db.entities.User

data class AuthResponse(
    val isSuccessful:Boolean?,
    val message:String?,
    val user:User?

)