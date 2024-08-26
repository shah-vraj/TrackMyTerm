package com.trackmyterm.data.repository

import com.trackmyterm.data.remote.apiresult.ApiResult
import com.trackmyterm.data.remote.apiservice.AuthService
import com.trackmyterm.data.remote.request.RegisterRequest
import com.trackmyterm.data.remote.response.RegisterResponse
import javax.inject.Inject

interface AuthenticationRepository {

    suspend fun registerUser(registerRequest: RegisterRequest): ApiResult<RegisterResponse>
}

class AuthenticationRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthenticationRepository {

    override suspend fun registerUser(
        registerRequest: RegisterRequest
    ): ApiResult<RegisterResponse> = authService.registerUser(registerRequest)
}