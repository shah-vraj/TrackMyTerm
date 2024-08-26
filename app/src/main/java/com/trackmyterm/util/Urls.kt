package com.trackmyterm.util

object Urls {
    private const val AUTH_PREFIX = "/api/auth"
    const val BASE_URL = "http://192.168.2.14:8080" // [TODO]: Change to deployed URL when ready

    // Endpoints
    const val REGISTER = "$AUTH_PREFIX/register"
}