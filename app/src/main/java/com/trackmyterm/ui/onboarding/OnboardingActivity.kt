package com.trackmyterm.ui.onboarding

import androidx.activity.viewModels
import com.trackmyterm.R
import com.trackmyterm.databinding.ActivityOnboardingBinding
import com.trackmyterm.ui.base.BaseAppCompatActivity
import com.trackmyterm.util.extensions.observeEvent
import com.trackmyterm.viewmodel.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : BaseAppCompatActivity<ActivityOnboardingBinding, OnboardingViewModel>() {

    override val viewModel: OnboardingViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_onboarding

    override fun setupViewModel() {
        viewModel.navigationEvent.observeEvent(this) {
            when (it) {
                OnboardingViewModel.Navigation.AuthenticationActivity -> {
                    // [TODO]: Navigate to auth activity
                }

                OnboardingViewModel.Navigation.HomeActivity -> {
                    // [TODO]: Navigate to home activity
                }
            }
        }
    }
}