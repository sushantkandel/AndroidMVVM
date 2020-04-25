package com.example.mvvmprac.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mvvmprac.data.repositories.UserRepository
import com.example.mvvmprac.util.ApiException
import com.example.mvvmprac.util.Coroutines
import com.example.mvvmprac.util.NoInternetException

class AuthViewModel(private val repository: UserRepository) : ViewModel() {
    var name: String? = null
    var email: String? = null
    var password: String? = null
    var confirmPassword: String? = null
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
            } catch (exc: NoInternetException) {
                authListener?.onFailure(exc.message.toString())
            }


        }


    }

    fun onLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignUP(view: View) {
        Intent(view.context, SignUpActivity::class.java).also {
            view.context.startActivity(it)
        }
    }


    fun onSignUpButtonClick(view: View) {
        authListener?.onStarted()

        if (name.isNullOrEmpty()) {
            authListener?.onFailure("name is required")
            return
        }

        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Email is Required")
            return
        }

        if (password.isNullOrEmpty()) {
            authListener?.onFailure("Password is required")
            return
        }

        if (password != confirmPassword) {
            authListener?.onFailure("Password not matched")
            return
        }


        Coroutines.main {
            try {
                val authResponse = repository.userSignUp(name!!, email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message.toString())
            } catch (exc: ApiException) {
                authListener?.onFailure(exc.message.toString())
            } catch (exc: NoInternetException) {
                authListener?.onFailure(exc.message.toString())
            }


        }
    }

}