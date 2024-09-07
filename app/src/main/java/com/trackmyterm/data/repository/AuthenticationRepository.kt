package com.trackmyterm.data.repository

import com.trackmyterm.data.remote.apiresult.ApiResult
import com.trackmyterm.data.remote.apiservice.AuthService
import com.trackmyterm.data.remote.request.ForgotPasswordRequest
import com.trackmyterm.data.remote.request.LoginRequest
import com.trackmyterm.data.remote.request.RegisterRequest
import com.trackmyterm.data.remote.response.ForgotPasswordResponse
import com.trackmyterm.data.remote.response.LoginResponse
import com.trackmyterm.data.remote.response.RegisterResponse
import javax.inject.Inject

interface AuthenticationRepository {

    suspend fun registerUser(request: RegisterRequest): ApiResult<RegisterResponse>

    suspend fun loginUser(request: LoginRequest): ApiResult<LoginResponse>

    suspend fun forgotPassword(request: ForgotPasswordRequest): ApiResult<ForgotPasswordResponse>
}

class AuthenticationRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthenticationRepository {

    override suspend fun registerUser(request: RegisterRequest): ApiResult<RegisterResponse> =
        authService.registerUser(request)

    override suspend fun loginUser(request: LoginRequest): ApiResult<LoginResponse> =
        authService.loginUser(request)

    override suspend fun forgotPassword(
        request: ForgotPasswordRequest
    ): ApiResult<ForgotPasswordResponse> = authService.forgotPassword(request)
}