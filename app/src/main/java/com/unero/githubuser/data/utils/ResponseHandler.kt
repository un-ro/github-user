package com.unero.ungithub.data.utils

import retrofit2.Response

object ResponseHandler {
    fun <T : Any> ifError(response: Response<T>): APIResponse.Error {
        val error = APIErrorUtils.parseError(response)
        return APIResponse.Error(Exception(error.message))
    }

    fun <T : Any> ifSuccess(response: Response<T>): APIResponse<T> {
        response.body()?.let {
            return APIResponse.Success(it)
        } ?: return ifError(response)
    }
}