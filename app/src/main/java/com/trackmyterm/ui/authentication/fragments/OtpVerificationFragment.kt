package com.trackmyterm.ui.authentication.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.otpview.OTPListener
import com.trackmyterm.R
import com.trackmyterm.databinding.FragmentOtpVerificationBinding
import com.trackmyterm.ui.base.BaseFragment
import com.trackmyterm.util.extensions.observeEvent
import com.trackmyterm.viewmodel.OtpVerificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpVerificationFragment :
    BaseFragment<FragmentOtpVerificationBinding, OtpVerificationViewModel>() {

    override val viewModel: OtpVerificationViewModel by viewModels()

    private val otpListener = object : OTPListener {
        override fun onInteractionListener() =
            viewModel.setOtp(binding.otpView.otp.orEmpty())

        override fun onOTPComplete(otp: String) { }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_otp_verification

    override fun initialize() {
        binding.otpView.otpListener = otpListener
        arguments?.let { bundle ->
            bundle.getString(EMAIL)?.let {
                viewModel.setEmail(it)
            }
        }
    }

    override fun setupViewModel() {
        viewModel.navigationEvent.observeEvent(this) {
            handleNavigation(it)
        }
    }

    private fun handleNavigation(navigation: OtpVerificationViewModel.Navigation) {
        when(navigation) {
            OtpVerificationViewModel.Navigation.Login -> findNavController().popBackStack()
            is OtpVerificationViewModel.Navigation.ResetPassword -> {
                // TODO: Navigate to reset password activity
            }
        }
    }

    companion object {
        const val EMAIL = "EMAIL"
    }
}