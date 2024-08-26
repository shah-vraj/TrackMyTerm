package com.trackmyterm.data.remote.response

data class ErrorResponse(
    override val resultType: String,
    override val data: Boolean,
    override val message: String
) : BaseResponse<Boolean>
