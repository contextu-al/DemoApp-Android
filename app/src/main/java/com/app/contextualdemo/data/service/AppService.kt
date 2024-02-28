package com.app.contextualdemo.data.service

import com.app.contextualdemo.domain.model.AppKeyValidationModel
import retrofit2.http.GET
import retrofit2.http.Path

interface AppService {
    @GET("/v3/apps/{app_key}/status")
    suspend fun validateAppKey(
        @Path("app_key") appKey: String
    ): AppKeyValidationModel
}