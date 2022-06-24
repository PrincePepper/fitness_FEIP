package com.example.fitness_feip.ui.main.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitness_feip.api.ApiRequest
import com.example.fitness_feip.api.TokenSP
import com.example.fitness_feip.api.User

class ProfileScreenViewModel(private val sharedPref: TokenSP) : ViewModel() {

    private val service = ApiRequest()

    private val _login = MutableLiveData<String>("")
    private val _name = MutableLiveData<String>("")
    private val _error = MutableLiveData<String>("")

    val login: LiveData<String> get() = _login
    val name: LiveData<String> get() = _name
    val error: LiveData<String> get() = _error

    private fun getProfileInfo() {
        val token = "Bearer ".plus(sharedPref.getToken())

        service.profile(token, object : ApiRequest.ProfileCallback {
            override fun onSuccess(result: User) {
                _login.value = result.login
                _name.value = result.name
            }

            override fun onError(error: Throwable) {
                _login.value = ""
                _name.value = ""
            }
        })
    }

    fun onLogoutClicked() {
        val token = "Bearer ".plus(sharedPref.getToken())

        service.logout(token, object : ApiRequest.LogoutCallback {
            override fun onSuccess(result: String) {
                sharedPref.cleanToken()
                _error.value = "Okay"
            }

            override fun onError(error: Throwable) {
                _error.value = error.toString()
            }
        })
    }

    init {
        getProfileInfo()
    }
}