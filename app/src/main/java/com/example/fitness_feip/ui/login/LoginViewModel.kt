package com.example.fitness_feip.ui.login

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitness_feip.api.ApiRequest
import com.example.fitness_feip.api.Login
import com.example.fitness_feip.api.TokenSP
import java.util.regex.Pattern


class LoginViewModel(private val sharedPref: TokenSP) : ViewModel() {
    private val PASSWORD_PATTERN: Pattern = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +  //at least 1 digit
                "(?=.*[a-z])" +  //at least 1 lower case letter
                "(?=.*[A-Z])" +  //at least 1 upper case letter
                "(?=\\S+$)" +  //no white spaces
                ".{6,}" +  //at least 4 characters
                "$"
    )

    private val service = ApiRequest()

    private val _showLoginError = MutableLiveData(0)
    private val _showPasswordError = MutableLiveData(0)
    private val _error = MutableLiveData("")

    val showLoginError: LiveData<Int> get() = _showLoginError
    val showPasswordError: LiveData<Int> get() = _showPasswordError
    val error: LiveData<String> get() = _error

    fun onLoginClicked(login: String, password: String) {
        var trigger = true
        if (TextUtils.isEmpty(login)) {
            _showLoginError.postValue(1)
            trigger = false
        } else _showLoginError.postValue(0)

        if (TextUtils.isEmpty(password)) {
            _showPasswordError.postValue(1)
            trigger = false
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            _showPasswordError.postValue(2)
            trigger = false
        } else _showPasswordError.postValue(0)

        if (trigger) service(login, password)
    }

    private fun service(login: String, password: String) {
        service.login(login, password, object : ApiRequest.LoginCallback {
            override fun onSuccess(result: Login) {
                sharedPref.saveToken(result.token)
                _error.value = "Okay"
            }

            override fun onError(error: Throwable) {
                _error.value = error.toString()
            }
        })
    }
}