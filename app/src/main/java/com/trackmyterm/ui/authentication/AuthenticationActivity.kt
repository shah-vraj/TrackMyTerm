package com.trackmyterm.ui.authentication

import androidx.activity.viewModels
import com.trackmyterm.R
import com.trackmyterm.databinding.ActivityAuthenticationBinding
import com.trackmyterm.ui.base.BaseAppCompatActivity
import com.trackmyterm.ui.home.HomeActivity
import com.trackmyterm.util.extensions.launchActivityAndFinish
import com.trackmyterm.util.extensions.observeEvent
import com.trackmyterm.viewmodel.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity :
    BaseAppCompatActivity<ActivityAuthenticationBinding, AuthenticationViewModel>() {

    override val viewModel: AuthenticationViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_authentication

    override fun setupViewModel() {
        viewModel.navigateToHomeActivity.observeEvent(this) {
            launchActivityAndFinish<HomeActivity>()
        }
    }
}