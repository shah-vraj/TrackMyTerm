package com.trackmyterm.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.trackmyterm.R
import com.trackmyterm.data.remote.request.ForgotPasswordRequest
import com.trackmyterm.data.repository.AuthenticationRepository
import com.trackmyterm.ui.base.BaseViewModel
import com.trackmyterm.util.components.ResourceHelper
import com.trackmyterm.util.extensions.onError
import com.trackmyterm.util.extensions.onException
import com.trackmyterm.util.extensions.onSuccess
import com.trackmyterm.util.result.Event
import com.trackmyterm.viewmodel.AuthenticationViewModel.Companion.API_ERROR_MESSAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val resourceHelper: ResourceHelper,
    private val authenticationRepository: AuthenticationRepository
) : BaseViewModel() {

    sealed interface Navigation {
        data class ResetPassword(val email: String) : Navigation
        data object Login : Navigation
    }

    val email = MutableLiveData("")

    private val _inputValidationError = MutableLiveData("")
    val inputValidationError: LiveData<String> get() = _inputValidationError

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _navigationEvent = MutableLiveData<Event<Navigation>>()
    val navigationEvent: LiveData<Event<Navigation>> get() = _navigationEvent

    val isInputValid = inputValidationError.map { it.isEmpty() }
    val sendOtpButtonText = isLoading.map { loading ->
        if (loading) "" else resourceHelper.getString(R.string.btn_send_otp)
    }

    fun onSendOtpClicked() {
        val email = email.value ?: return
        validateInput(email)
        if (isInputValid.value != true)
            return
        sendOtp(ForgotPasswordRequest(email))
    }

    fun onBackToLoginClicked() {
        _navigationEvent.value = Event(Navigation.Login)
    }

    private fun sendOtp(request: ForgotPasswordRequest) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            authenticationRepository.forgotPassword(request)
                .onSuccess { onSendOtpSuccess(request.email) }
                .onError { _, message -> setInputValidationError(message ?: "") }
                .onException { setInputValidationError(API_ERROR_MESSAGE) }
            _isLoading.postValue(false)
        }
    }

    private fun validateInput(email: String): Unit = when {
        email.isBlank() ->
            setInputValidationError(resourceHelper.getString(R.string.txt_empty_input_field))

        !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
            setInputValidationError(resourceHelper.getString(R.string.txt_invalid_email))

        else -> setInputValidationError("")
    }

    private fun setInputValidationError(error: String) {
        _inputValidationError.value = error
    }

    private fun onSendOtpSuccess(email: String) {
        _navigationEvent.value = Event(Navigation.ResetPassword(email))
    }
}