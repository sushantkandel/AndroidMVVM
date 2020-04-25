package com.example.mvvmprac.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmprac.R
import com.example.mvvmprac.data.db.AppDatabase
import com.example.mvvmprac.data.db.entities.User
import com.example.mvvmprac.data.network.MyApi
import com.example.mvvmprac.data.network.NetworkConnectionInterceptor
import com.example.mvvmprac.data.repositories.UserRepository
import com.example.mvvmprac.databinding.ActivityLoginBinding
import com.example.mvvmprac.ui.home.HomeActivity
import com.example.mvvmprac.util.hide
import com.example.mvvmprac.util.show
import com.example.mvvmprac.util.showSnackBar
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), AuthListener,KodeinAware {

    override val kodein by kodein()
    private val factory:AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this
        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }

            }

        })
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(user: User) {
        progress_bar.hide()
        // root_layout.showSnackBar("${user.name} is Logged In")

    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.showSnackBar(message)

    }


}
