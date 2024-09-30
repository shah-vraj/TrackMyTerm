package com.trackmyterm.data.remote.apiservice

import com.trackmyterm.data.remote.apiresult.ApiResult
import com.trackmyterm.data.remote.request.ForgotPasswordRequest
import com.trackmyterm.data.remote.request.LoginRequest
import com.trackmyterm.data.remote.request.OtpVerificationRequest
import com.trackmyterm.data.remote.request.RegisterRequest
import com.trackmyterm.data.remote.response.ForgotPasswordResponse
import com.trackmyterm.data.remote.response.LoginResponse
import com.trackmyterm.data.remote.response.OtpVerificationResponse
import com.trackmyterm.data.remote.response.RegisterResponse
import com.trackmyterm.util.Urls
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Provides remote APIs
 */
interface AuthService {

    @POST(Urls.REGISTER)
    suspend fun registerUser(@Body request: RegisterRequest): ApiResult<RegisterResponse>

    @POST(Urls.LOGIN)
    suspend fun loginUser(@Body request: LoginRequest): ApiResult<LoginResponse>

    @POST(Urls.FORGOT_PASSWORD)
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): ApiResult<ForgotPasswordResponse>

    @POST(Urls.OTP_VERIFICATION)
    suspend fun verifyOtp(@Body request: OtpVerificationRequest): ApiResult<OtpVerificationResponse>
}