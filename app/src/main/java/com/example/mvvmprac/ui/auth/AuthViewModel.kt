package com.example.mvvmprac.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mvvmprac.data.repositories.UserRepository
import com.example.mvvmprac.util.ApiException
import com.example.mvvmprac.util.Coroutines
import com.example.mvvmprac.util.NoInternetException

class AuthViewModel(private val repository: UserRepository) : ViewModel() {
    var email: String? = null
    var password: String? = null
    var authListener: AuthListener? = null

    fun getLoggedInUser() = repository.getUser()


    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Email or Password Empty")
            return
        }
        Coroutines.main {
            try {
                val authResponse = repository.userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message.toString())
            } catch (exc: ApiException) {
                authListener?.onFailure(exc.message.toString())
            }catch (exc:NoInternetException){
                authListener?.onFailure(exc.message.toString())
            }


        }


    }

    fun onSignUp(view: View) {
        authListener?.onFailure("loginResponse")
    }

}