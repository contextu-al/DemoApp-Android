package com.app.contextualdemo.data.repository

import com.app.contextualdemo.data.service.AppService
import com.app.contextualdemo.domain.model.AppKeyValidationModel
import com.app.contextualdemo.domain.repository.AppKeyValidationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AppKeyValidationModelImpl(
    private val appService: AppService
): AppKeyValidationRepository {

    override fun validate(appKey: String): Flow<AppKeyValidationModel> {
        return flow {
            emit(appService.validateAppKey(appKey))
        }
            .flowOn(Dispatchers.IO)
    }
}