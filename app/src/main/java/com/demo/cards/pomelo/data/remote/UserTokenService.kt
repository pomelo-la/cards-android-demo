package com.demo.cards.pomelo.data.remote

import com.demo.cards.pomelo.data.entities.UserTokenBody
import com.demo.cards.pomelo.data.entities.UserTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserTokenService {
    @POST("token")
    suspend fun getUserToken(
        @Body body: UserTokenBody
    ): Response<UserTokenResponse>
}
