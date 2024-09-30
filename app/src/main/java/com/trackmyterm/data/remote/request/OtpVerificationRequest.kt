package com.trackmyterm.data.remote.request

data class OtpVerificationRequest(
    val otp: String,
    val email: String
)