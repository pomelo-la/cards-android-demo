package com.demo.cards.pomelo.data.entities

import com.squareup.moshi.Json

data class UserTokenResponse(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "expires_in") val expiresIn: Int,
    @Json(name = "token_type") val tokenType: String
)
