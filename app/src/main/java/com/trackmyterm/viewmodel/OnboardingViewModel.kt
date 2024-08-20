package com.trackmyterm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.trackmyterm.ui.base.BaseViewModel
import com.trackmyterm.util.components.AppSharedPreferences
import com.trackmyterm.util.components.NetworkHelper
import com.trackmyterm.util.result.Event
import com.trackmyterm.viewmodel.OnboardingViewModel.Navigation.AuthenticationActivity
import com.trackmyterm.viewmodel.OnboardingViewModel.Navigation.HomeActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val appSharedPreferences: AppSharedPreferences,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    sealed interface Navigation {
        data object AuthenticationActivity : Navigation
        data object HomeActivity : Navigation
    }

    private val _navigationEvent = MutableLiveData<Event<Navigation>>()
    val navigationEvent: LiveData<Event<Navigation>> get() = _navigationEvent

    private val _isInternetAvailable = MutableLiveData<Boolean>()
    val isInternetAvailable: LiveData<Boolean> get() = _isInternetAvailable

    init {
        if (isNetworkAvailable())
            checkOnboardingCompletionStatus()
    }

    fun onStartClicked() {
        _navigationEvent.value = Event(AuthenticationActivity)
        appSharedPreferences.setOnboardingCompleted()
    }

    private fun isNetworkAvailable(): Boolean =
        networkHelper.isNetworkAvailable().also { _isInternetAvailable.value = it }

    private fun checkOnboardingCompletionStatus() {
        val isOnboardingCompleted = appSharedPreferences.isOnboardingCompleted()
        val isUserLoggedIn = appSharedPreferences.isUserLoggedIn()
        if (isOnboardingCompleted) {
            _navigationEvent.value = Event(
                if (isUserLoggedIn) HomeActivity else AuthenticationActivity
            )
        }
    }
}