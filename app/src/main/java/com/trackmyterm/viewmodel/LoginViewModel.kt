package com.trackmyterm.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.trackmyterm.R
import com.trackmyterm.data.remote.request.LoginRequest
import com.trackmyterm.data.remote.response.LoginResponse
import com.trackmyterm.data.repository.AuthenticationRepository
import com.trackmyterm.ui.base.BaseViewModel
import com.trackmyterm.util.components.AppSharedPreferences
import com.trackmyterm.util.components.ResourceHelper
import com.trackmyterm.util.extensions.onError
import com.trackmyterm.util.extensions.onException
import com.trackmyterm.util.extensions.onSuccess
import com.trackmyterm.util.result.Event
import com.trackmyterm.viewmodel.AuthenticationViewModel.Companion.API_ERROR_MESSAGE
import com.trackmyterm.viewmodel.AuthenticationViewModel.Companion.MIN_PASSWORD_LENGTH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val resourceHelper: ResourceHelper,
    private val authenticationRepository: AuthenticationRepository,
    private val appSharedPreferences: AppSharedPreferences
): BaseViewModel() {

    sealed interface Navigation {
        data object RegisterActivity : Navigation
        data object HomeActivity : Navigation
        data object ForgotPassword : Navigation
    }

    val email = MutableLiveData(appSharedPreferences.getRememberedEmail() ?: "")
    val password = MutableLiveData("")

    private val _passwordHiddenStatus = MutableLiveData(true)
    val passwordHiddenStatus: LiveData<Boolean> get() = _passwordHiddenStatus

    private val _rememberMeStatus = MutableLiveData(false)
    val rememberMeStatus: LiveData<Boolean> get() = _rememberMeStatus

    private val _inputValidationError = MutableLiveData("")
    val inputValidationError: LiveData<String> get() = _inputValidationError

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _navigationEvent = MutableLiveData<Event<Navigation>>()
    val navigationEvent: LiveData<Event<Navigation>> get() = _navigationEvent

    val isInputValid = inputValidationError.map { it.isEmpty() }
    val loginButtonText = isLoading.map { loading ->
        if (loading) "" else resourceHelper.getString(R.string.btn_login)
    }

    fun onPasswordEyeClicked() {
        _passwordHiddenStatus.value = !(passwordHiddenStatus.value ?: false)
    }

    fun onRememberMeClicked() {
        _rememberMeStatus.value = !(rememberMeStatus.value ?: false)
    }

    fun onForgotPasswordClicked() {
        if (isLoading.value == true) return
        _navigationEvent.value = Event(Navigation.ForgotPassword)
    }

    fun onLoginClicked() {
        val email = email.value ?: return
        val password = password.value ?: return
        validateInput(email, password)
        if (isInputValid.value != true)
            return
        loginUser(LoginRequest(email, password))
    }

    fun navigateToRegister() {
        if (isLoading.value == true) return
        _navigationEvent.value = Event(Navigation.RegisterActivity)
    }

    private fun validateInput(email: String, password: String): Unit = when {
        email.isBlank() || password.isBlank() ->
            setInputValidationError(resourceHelper.getString(R.string.txt_empty_input_field))

        !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
            setInputValidationError(resourceHelper.getString(R.string.txt_invalid_email))

        password.length < MIN_PASSWORD_LENGTH ->
            setInputValidationError(resourceHelper.getString(R.string.txt_invalid_password))

        else -> setInputValidationError("")
    }

    private fun loginUser(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            authenticationRepository.loginUser(loginRequest)
                .onSuccess { onLoginSuccess(loginRequest.email, it) }
                .onError { _, message -> setInputValidationError(message ?: "") }
                .onException { setInputValidationError(API_ERROR_MESSAGE) }
            _isLoading.postValue(false)
        }
    }

    private fun setInputValidationError(error: String) {
        _inputValidationError.value = error
    }

    private fun onLoginSuccess(email: String, loginResponse: LoginResponse) {
        setInputValidationError("")
        appSharedPreferences.apply {
            setAuthToken(loginResponse.data.token)
            setUserLoggedIn(true)
            if (rememberMeStatus.value == true)
                setRememberedEmail(email)
        }
        _navigationEvent.value = Event(Navigation.HomeActivity)
    }
}