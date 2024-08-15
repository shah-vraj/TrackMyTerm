package com.trackmyterm.di

import com.trackmyterm.util.NetworkHelper
import com.trackmyterm.util.NetworkHelperImpl
import com.trackmyterm.util.ResourceHelper
import com.trackmyterm.util.ResourceHelperImpl
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
}