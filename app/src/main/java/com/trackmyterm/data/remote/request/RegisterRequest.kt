package com.trackmyterm.data.remote.request

data class RegisterRequest(
    val userName: String,
    val email: String,
    val password: String
)
