package com.app.contextualdemo.domain.model

import com.google.gson.annotations.SerializedName

data class AppKeyValidationModel(
    @SerializedName("feed")
    val feed: String?
)
