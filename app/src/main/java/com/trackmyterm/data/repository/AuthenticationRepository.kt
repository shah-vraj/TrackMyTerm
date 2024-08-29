package com.trackmyterm.data.repository

import com.trackmyterm.data.remote.apiresult.ApiResult
import com.trackmyterm.data.remote.apiservice.AuthService
import com.trackmyterm.data.remote.request.LoginRequest
import com.trackmyterm.data.remote.request.RegisterRequest
import com.trackmyterm.data.remote.response.LoginResponse
import com.trackmyterm.data.remote.response.RegisterResponse
import javax.inject.Inject

interface AuthenticationRepository {

    suspend fun registerUser(registerRequest: RegisterRequest): ApiResult<RegisterResponse>

    suspend fun loginUser(loginRequest: LoginRequest): ApiResult<LoginResponse>
}

class AuthenticationRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthenticationRepository {

    override suspend fun registerUser(registerRequest: RegisterRequest): ApiResult<RegisterResponse> =
        authService.registerUser(registerRequest)

    override suspend fun loginUser(loginRequest: LoginRequest): ApiResult<LoginResponse> =
        authService.loginUser(loginRequest)
}