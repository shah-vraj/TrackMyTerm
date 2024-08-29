package com.trackmyterm.ui.home

import androidx.activity.viewModels
import com.trackmyterm.R
import com.trackmyterm.databinding.ActivityHomeBinding
import com.trackmyterm.ui.base.BaseAppCompatActivity
import com.trackmyterm.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseAppCompatActivity<ActivityHomeBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_home
}