package com.trackmyterm.ui.authentication.fragments

import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.trackmyterm.R
import com.trackmyterm.databinding.FragmentLoginBinding
import com.trackmyterm.ui.base.BaseFragment
import com.trackmyterm.util.extensions.observeEvent
import com.trackmyterm.viewmodel.AuthenticationViewModel
import com.trackmyterm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()
    private val authViewModel: AuthenticationViewModel by activityViewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_login

    override fun initialize() {
        setupArguments()
    }

    override fun setupViewModel() {
        viewModel.apply {
            passwordHiddenStatus.observe(this@LoginFragment) {
                binding.updatePasswordTextInput(it)
            }
            rememberMeStatus.observe(this@LoginFragment) {
                binding.ivRememberMe.isSelected = it
            }
            navigationEvent.observeEvent(this@LoginFragment) {
                when (it) {
                    LoginViewModel.Navigation.HomeActivity ->
                        authViewModel.navigateToHomeActivity()
                    LoginViewModel.Navigation.RegisterActivity ->
                        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                }
            }
        }
    }

    private fun setupArguments() {
        arguments?.getString(EMAIL_KEY)?.let {
            if (it.isBlank()) return
            binding.etEmail.setText(it)
        }
    }

    private fun FragmentLoginBinding.updatePasswordTextInput(isPasswordHidden: Boolean) {
        etPassword.apply {
            transformationMethod = if (isPasswordHidden) PasswordTransformationMethod() else null
            setSelection(text?.length ?: 0)
        }
        ivPasswordEye.isSelected = !isPasswordHidden
    }

    companion object {
        const val EMAIL_KEY = "email"
    }
}