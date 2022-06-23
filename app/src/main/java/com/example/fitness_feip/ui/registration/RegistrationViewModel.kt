package com.example.fitness_feip.ui.registration

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitness_feip.api.ApiRequest
import com.example.fitness_feip.api.Login
import com.example.fitness_feip.api.TokenSP
import java.util.regex.Pattern

class RegistrationViewModel(private val sharedPref: TokenSP) : ViewModel() {
    var PASSWORD_PATTERN: Pattern = Pattern.compile(
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
    private val _showNameError = MutableLiveData(false)
    private val _showPasswordError = MutableLiveData(0)
    private val _showRepeatPasswordError = MutableLiveData(false)
    private val _showWrongRepeatPasswordError = MutableLiveData(false)
    private val _error = MutableLiveData("")

    val showLoginError: LiveData<Int> get() = _showLoginError
    val showNameError: LiveData<Boolean> get() = _showNameError
    val showPasswordError: LiveData<Int> get() = _showPasswordError
    val showRepeatPasswordError: LiveData<Boolean> get() = _showRepeatPasswordError
    val showWrongRepeatPasswordError: LiveData<Boolean> get() = _showWrongRepeatPasswordError
    val error: LiveData<String> get() = _error

    fun onRegistrationClicked(
        login: String,
        name: String,
        password: String,
        passwordRepeat: String,
        gender: Int
    ) {

        if (TextUtils.isEmpty(login)) {
            _showLoginError.postValue(1)
        } else _showLoginError.postValue(0)

        if (name.isBlank()) {
            _showNameError.postValue(true)
        } else _showNameError.postValue(false)

        if (TextUtils.isEmpty(password)) {
            _showPasswordError.postValue(1)
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            _showPasswordError.postValue(2)
        } else _showPasswordError.postValue(0)

        if (passwordRepeat.isBlank()) {
            _showRepeatPasswordError.postValue(true)
        } else _showRepeatPasswordError.postValue(false)

        if (passwordRepeat != password) {
            _showWrongRepeatPasswordError.postValue(true)
        } else _showWrongRepeatPasswordError.postValue(false)

        service.register(
            login,
            password,
            name,
            gender,
            object : ApiRequest.LoginCallback {
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