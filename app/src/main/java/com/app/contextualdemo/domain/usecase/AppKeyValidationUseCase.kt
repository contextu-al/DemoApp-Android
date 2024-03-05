package com.app.contextualdemo.domain.usecase

import com.app.contextualdemo.domain.model.AppKeyValidationModel
import com.app.contextualdemo.domain.repository.AppKeyValidationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppKeyValidationUseCase @Inject constructor(
    private val appKeyValidationRepository: AppKeyValidationRepository
) {
    fun invoke(appKey: String): Flow<AppKeyValidationModel> {
        return appKeyValidationRepository.validate(appKey)
    }
}