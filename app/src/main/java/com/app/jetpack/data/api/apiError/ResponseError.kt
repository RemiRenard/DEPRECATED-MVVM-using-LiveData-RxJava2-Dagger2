package com.app.jetpack.data.api.apiError

/**
 * Sent by the Web Api when an error occurs.
 */
class ResponseError {

    var isSuccess: Boolean = false
    var error: Error? = null

    inner class Error {

        var code: Int? = null
        var message: String? = null
    }
}

