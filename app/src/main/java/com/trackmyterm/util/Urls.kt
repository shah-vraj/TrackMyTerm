package com.trackmyterm.util

object Urls {
    private const val AUTH_PREFIX = "/api/auth"
    const val BASE_URL = "http://192.168.4.93:8080" // [TODO]: Change to deployed URL when ready

    // Endpoints
    const val REGISTER = "$AUTH_PREFIX/register"
    const val LOGIN = "$AUTH_PREFIX/login"
    const val FORGOT_PASSWORD = "$AUTH_PREFIX/forgot-password"
    const val OTP_VERIFICATION = "$AUTH_PREFIX/verify-otp"
}