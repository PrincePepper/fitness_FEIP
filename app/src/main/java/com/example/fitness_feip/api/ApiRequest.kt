package com.example.fitness_feip.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class ApiRequest {

    private val api = Network().retrofit.create(Api::class.java)

    fun register(
        login: String,
        password: String,
        name: String,
        gender: Int,
        callback: LoginCallback
    ) {
        api.register(login, password, name, gender)
            .enqueue(object : Callback<Login> {
                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    if (response.isSuccessful) {
                        response.body()?.let { callback.onSuccess(it) } ?: callback.onError(
                            IOException("Server returned error")
                        )
                    } else response.errorBody()
                        ?.string()//callback.onError(IOException("Empty body"))
                }

                override fun onFailure(call: Call<Login>, t: Throwable) {
                    callback.onError(t)
                }
            })
    }

    fun login(
        login: String,
        password: String,
        callback: LoginCallback
    ) {
        api.login(login, password).enqueue(object : Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.onSuccess(it) }
                        ?: callback.onError(IOException("Server returned error"))

                } else response.errorBody()?.string()//callback.onError(IOException("Empty body"))

            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                callback.onError(t)
            }
        })
    }

    fun logout(token: String, callback: LogoutCallback) {
        api.logout(token).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 200) {
                    if (response.message() == "OK") {
                        callback.onSuccess(response.message())
                    } else {
                        callback.onError(IOException("Server returned error"))
                    }
                } else callback.onError(IOException("Empty body"))
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback.onError(t)
            }
        })
    }

    fun profile(token: String, callback: ProfileCallback) {
        api.profile(token).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.onSuccess(it) }
                        ?: callback.onError(IOException("Server returned error"))

                } else callback.onError(IOException("Empty body"))
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback.onError(t)
            }
        })
    }

    interface LoginCallback {
        fun onSuccess(result: Login)
        fun onError(error: Throwable)
    }

    interface LogoutCallback {
        fun onSuccess(result: String)
        fun onError(error: Throwable)
    }

    interface ProfileCallback {
        fun onSuccess(result: User)
        fun onError(error: Throwable)
    }
}