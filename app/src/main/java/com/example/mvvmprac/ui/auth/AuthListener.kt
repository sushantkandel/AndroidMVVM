package com.example.mvvmprac.ui.auth

import androidx.lifecycle.LiveData
import com.example.mvvmprac.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message:String)
}