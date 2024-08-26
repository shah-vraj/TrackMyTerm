package com.trackmyterm.ui.authentication.fragments

import android.text.method.PasswordTransformationMethod
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.trackmyterm.R
import com.trackmyterm.databinding.FragmentRegisterBinding
import com.trackmyterm.ui.base.BaseFragment
import com.trackmyterm.util.extensions.observeEvent
import com.trackmyterm.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>() {

    override val viewModel: RegisterViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_register

    override fun setupViewModel() {
        viewModel.apply {
            passwordHiddenStatus.observe(this@RegisterFragment) {
                binding.updatePasswordTextInput(it)
            }
            navigateToLogin.observeEvent(this@RegisterFragment) {
                findNavController().navigate(
                    R.id.action_registerFragment_to_loginFragment,
                    bundleOf(LoginFragment.EMAIL_KEY to it)
                )
            }
        }
    }

    private fun FragmentRegisterBinding.updatePasswordTextInput(isPasswordHidden: Boolean) {
        etPassword.apply {
            transformationMethod = if (isPasswordHidden) PasswordTransformationMethod() else null
            setSelection(text?.length ?: 0)
        }
        ivPasswordEye.isSelected = !isPasswordHidden
    }
}
