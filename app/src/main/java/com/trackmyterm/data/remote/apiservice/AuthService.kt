package com.trackmyterm.data.remote.apiservice

import com.trackmyterm.data.remote.apiresult.ApiResult
import com.trackmyterm.data.remote.request.RegisterRequest
import com.trackmyterm.data.remote.response.RegisterResponse
import com.trackmyterm.util.Urls
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Provides remote APIs
 */
interface AuthService {

    @POST(Urls.REGISTER)
    suspend fun registerUser(@Body registerRequest: RegisterRequest): ApiResult<RegisterResponse>
}