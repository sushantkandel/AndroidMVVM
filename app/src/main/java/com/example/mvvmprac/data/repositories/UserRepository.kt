package com.example.mvvmprac.data.repositories

import com.example.mvvmprac.data.db.AppDatabase
import com.example.mvvmprac.data.db.entities.User
import com.example.mvvmprac.data.network.MyApi
import com.example.mvvmprac.data.network.SafeApiRequest
import com.example.mvvmprac.data.network.response.AuthResponse
import com.example.mvvmprac.util.ApiException
import retrofit2.Response

class UserRepository(
    private val myApi: MyApi,
    private val db: AppDatabase
) : SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest {
            myApi.userLogin(email, password)
        }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)
    fun getUser() = db.getUserDao().getUser()

    /**
    fun userLogin(email: String, password: String): LiveData<String> {
    val loginResponse = MutableLiveData<String>()
    MyApi().userLogin(email, password)
    .enqueue(object : Callback<ResponseBody> {
    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
    loginResponse.value = t.message
    }

    override fun onResponse(
    call: Call<ResponseBody>,
    response: Response<ResponseBody>
    ) {
    if (response.isSuccessful) {
    loginResponse.value = response.body()?.string()
    } else {
    loginResponse.value = response.errorBody()?.string()
    }

    }

    })

    return loginResponse
    }*/
}