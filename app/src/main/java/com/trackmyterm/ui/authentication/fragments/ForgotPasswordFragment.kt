package com.trackmyterm.ui.authentication.fragments

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.trackmyterm.R
import com.trackmyterm.databinding.FragmentForgotPasswordBinding
import com.trackmyterm.ui.base.BaseFragment
import com.trackmyterm.util.extensions.observeEvent
import com.trackmyterm.viewmodel.ForgotPasswordViewModel
import com.trackmyterm.viewmodel.ForgotPasswordViewModel.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseFragment<FragmentForgotPasswordBinding, ForgotPasswordViewModel>() {

    override val viewModel: ForgotPasswordViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_forgot_password

    override fun setupViewModel() {
        viewModel.navigationEvent.observeEvent(this) {
            handleNavigation(it)
        }
    }

    private fun handleNavigation(navigation: Navigation) {
        when (navigation) {
            Navigation.Login -> parentFragmentManager.popBackStack()
            is Navigation.OtpVerification ->
                findNavController().navigate(
                    resId = R.id.action_forgotPasswordFragment_to_otpVerificationFragment,
                    args = bundleOf(OtpVerificationFragment.EMAIL to navigation.email)
                )
        }
    }
}
