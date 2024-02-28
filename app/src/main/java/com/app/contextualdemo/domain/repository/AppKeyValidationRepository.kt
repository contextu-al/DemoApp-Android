package com.app.contextualdemo.domain.repository

import com.app.contextualdemo.domain.model.AppKeyValidationModel
import kotlinx.coroutines.flow.Flow

interface AppKeyValidationRepository {
    fun validate(appKey: String): Flow<AppKeyValidationModel>
}