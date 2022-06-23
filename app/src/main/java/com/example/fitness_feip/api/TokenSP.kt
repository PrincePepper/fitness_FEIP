package com.example.fitness_feip.api

import android.content.Context
import android.content.SharedPreferences

class TokenSP(context: Context?) {

    companion object {
        const val AUTH = "auth"
    }

    private val sharedPreferences = context?.getSharedPreferences(AUTH, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString(AUTH, token)
        editor.apply()
    }

    fun getToken(): String {
        return sharedPreferences?.getString(AUTH, "")!!
    }

    fun checkToken(): Boolean {
        return getToken() != ""
    }

    fun cleanToken() {
        sharedPreferences?.edit()?.remove(AUTH)?.apply()
    }
}