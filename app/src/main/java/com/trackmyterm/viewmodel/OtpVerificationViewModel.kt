package com.trackmyterm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.trackmyterm.R
import com.trackmyterm.data.remote.request.OtpVerificationRequest
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
class OtpVerificationViewModel @Inject constructor(
    private val resourceHelper: ResourceHelper,
    private val authenticationRepository: AuthenticationRepository
) : BaseViewModel() {

    sealed interface Navigation {
        data class ResetPassword(val email: String) : Navigation
        data object Login : Navigation
    }

    val email = MutableLiveData("")
    private val otp = MutableLiveData("")

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _navigationEvent = MutableLiveData<Event<Navigation>>()
    val navigationEvent: LiveData<Event<Navigation>> get() = _navigationEvent

    private val _inputValidationError = MutableLiveData("")
    val inputValidationError: LiveData<String> get() = _inputValidationError

    val isInputValid = inputValidationError.map { it.isEmpty() }
    val resetPasswordButtonText = isLoading.map { loading ->
        if (loading) "" else resourceHelper.getString(R.string.btn_send_otp)
    }

    fun setEmail(email: String) {
        this.email.value = email
    }

    fun setOtp(otp: String) {
        this.otp.value = otp
    }

    fun onBackToLoginClicked() {
        _navigationEvent.value = Event(Navigation.Login)
    }

    fun onResetPasswordClicked() {
        val otp = otp.value ?: return
        val email = email.value ?: return
        validateInput(otp, email)
        if (isInputValid.value != true)
            return
        verifyOtp(OtpVerificationRequest(otp, email))
    }

    private fun verifyOtp(request: OtpVerificationRequest) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            authenticationRepository.verifyOtp(request)
                .onSuccess { onSendOtpSuccess(request.email) }
                .onError { _, message -> setInputValidationError(message ?: "") }
                .onException { setInputValidationError(API_ERROR_MESSAGE) }
            _isLoading.postValue(false)
        }
    }

    private fun validateInput(otp: String, email: String) {
        when {
            otp.isEmpty() || otp.length != OTP_LENGTH || email.isEmpty() ->
                setInputValidationError(resourceHelper.getString(R.string.txt_input_valid_otp))
            else -> setInputValidationError("")
        }
    }

    private fun setInputValidationError(error: String) {
        _inputValidationError.value = error
    }

    private fun onSendOtpSuccess(email: String) {
        _navigationEvent.value = Event(Navigation.ResetPassword(email))
    }

    companion object {
        private const val OTP_LENGTH = 6
    }
}
