package com.trackmyterm.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.trackmyterm.R
import com.trackmyterm.data.remote.request.RegisterRequest
import com.trackmyterm.data.repository.AuthenticationRepository
import com.trackmyterm.ui.base.BaseViewModel
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
class RegisterViewModel @Inject constructor(
    private val resourceHelper: ResourceHelper,
    private val authenticationRepository: AuthenticationRepository
): BaseViewModel() {

    val fullName = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")

    private val _passwordHiddenStatus = MutableLiveData(true)
    val passwordHiddenStatus: LiveData<Boolean> get() = _passwordHiddenStatus

    private val _inputValidationError = MutableLiveData("")
    val inputValidationError: LiveData<String> get() = _inputValidationError

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _navigateToLogin = MutableLiveData<Event<String>>()
    val navigateToLogin: LiveData<Event<String>> get() = _navigateToLogin

    val isInputValid = inputValidationError.map { it.isEmpty() }
    val signupButtonText = isLoading.map { loading ->
        if (loading) "" else resourceHelper.getString(R.string.btn_sign_up)
    }

    fun onPasswordEyeClicked() {
        _passwordHiddenStatus.value = !(passwordHiddenStatus.value ?: false)
    }

    fun onSignUpClicked() {
        val fullName = fullName.value ?: return
        val email = email.value ?: return
        val password = password.value ?: return
        validateInput(fullName, email, password)
        if (isInputValid.value != true)
            return
        registerUser(RegisterRequest(fullName, email, password))
    }

    fun navigateToLogin() {
        _navigateToLogin.value = Event("")
    }

    private fun validateInput(fullName: String, email: String, password: String): Unit = when {
        fullName.isBlank() || email.isBlank() || password.isBlank() ->
            setInputValidationError(resourceHelper.getString(R.string.txt_empty_input_field))

        !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
            setInputValidationError(resourceHelper.getString(R.string.txt_invalid_email))

        password.length < MIN_PASSWORD_LENGTH ->
            setInputValidationError(resourceHelper.getString(R.string.txt_invalid_password))

        else -> setInputValidationError("")
    }

    private fun registerUser(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            authenticationRepository.registerUser(registerRequest)
                .onSuccess { onRegisterSuccess(registerRequest.email) }
                .onError { _, message -> setInputValidationError(message ?: "") }
                .onException { setInputValidationError(API_ERROR_MESSAGE) }
            _isLoading.postValue(false)
        }
    }

    private fun setInputValidationError(error: String) {
        _inputValidationError.value = error
    }

    private fun onRegisterSuccess(email: String) {
        setInputValidationError("")
        _navigateToLogin.value = Event(email)
    }
}