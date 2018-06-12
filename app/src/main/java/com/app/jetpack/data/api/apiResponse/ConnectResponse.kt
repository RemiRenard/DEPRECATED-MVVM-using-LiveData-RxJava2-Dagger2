package com.app.jetpack.data.api.apiResponse

import com.app.jetpack.data.model.User

/**
 * Response from the API when the user wants to login.
 */
class ConnectResponse {

    var token: String? = null
    var user: User? = null
}
