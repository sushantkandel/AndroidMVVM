package com.example.mvvmprac.ui.home.profile

import androidx.lifecycle.ViewModel
import com.example.mvvmprac.data.repositories.UserRepository

class ProfileViewModel(repository: UserRepository) : ViewModel() {
    val user =repository.getUser()
}
