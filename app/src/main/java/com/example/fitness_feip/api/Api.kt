package com.example.fitness_feip.api

import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {

    @POST("/api/auth/register")
    fun register(
        @Query("login") login: String,
        @Query("password") password: String,
        @Query("name") name: String,
        @Query("gender") gender: Int
    ): Call<Login>

    @POST("/api/auth/login")
    fun login(
        @Query("login") login: String,
        @Query("password") password: String
    ): Call<Login>

    @POST("/api/auth/logout")
    fun logout(
        @Header("Authorization") token: String
    ): Call<Void>

    @GET("/api/user/profile")
    fun profile(
        @Header("Authorization") token: String
    ): Call<User>

}

data class Login(
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: User
)

data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("login")
    val login: String,
    @SerializedName("gender")
    val gender: Gender,
)

data class Gender(
    @SerializedName("code")
    val code: Int,
    @SerializedName("name")
    val name: String
)

class Network {

    private val logger = HttpLoggingInterceptor()

    private val httpClient =
        OkHttpClient.Builder()
            .addInterceptor(logger.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://fefu.t.feip.co")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}


