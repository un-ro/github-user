package com.unero.ungithub.data.utils

import android.util.Log
import com.google.gson.GsonBuilder
import okio.IOException
import retrofit2.Response

object APIErrorUtils {

    fun parseError(response: Response<*>): ErrorMessage {

        val dataGson = GsonBuilder().create()
        val errorMessage: ErrorMessage

        try {
            errorMessage = dataGson.fromJson(response.errorBody()?.string(), ErrorMessage::class.java)
        } catch (e: IOException) {
            e.message?.let { Log.d("ERROR API", it) }
            return ErrorMessage()
        }
        return errorMessage
    }
}

data class ErrorMessage(val message: String) {
    constructor() : this("")
}