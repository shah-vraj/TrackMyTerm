package com.trackmyterm.ui.authentication.fragments

import androidx.fragment.app.viewModels
import com.trackmyterm.R
import com.trackmyterm.databinding.FragmentLoginBinding
import com.trackmyterm.ui.base.BaseFragment
import com.trackmyterm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_login

    companion object {
        const val EMAIL_KEY = "email"
    }
}