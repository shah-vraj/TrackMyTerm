package com.trackmyterm.di

import com.trackmyterm.data.repository.AuthenticationRepository
import com.trackmyterm.data.repository.AuthenticationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Defines all the repositories that need to be provided in the scope of the app.
 * If they are singleton mark them as '@Singleton'.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideAuthenticationRepository(
        authenticationRepository: AuthenticationRepositoryImpl
    ): AuthenticationRepository
}