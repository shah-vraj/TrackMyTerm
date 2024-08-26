package com.trackmyterm.data.remote.response

interface BaseResponse<T> {
    val resultType: String
    val data: T
    val message: String
}