package com.trackmyterm.di

import com.trackmyterm.util.components.AppSharedPreferences
import com.trackmyterm.util.components.AppSharedPreferencesImpl
import com.trackmyterm.util.components.NetworkHelper
import com.trackmyterm.util.components.NetworkHelperImpl
import com.trackmyterm.util.components.ResourceHelper
import com.trackmyterm.util.components.ResourceHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

/**
 * Defines all the classes that need to be provided in the scope of the app.
 * If they are singleton mark them as '@Singleton'.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindingModule {

    @Singleton
    @Binds
    abstract fun bindResourceHelper(impl: ResourceHelperImpl): ResourceHelper

    @Singleton
    @Binds
    abstract fun bindNetworkHelper(impl: NetworkHelperImpl): NetworkHelper

    @Singleton
    @Binds
    abstract fun bindSharedPreferences(impl: AppSharedPreferencesImpl): AppSharedPreferences
}