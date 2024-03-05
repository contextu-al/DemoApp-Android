package com.app.contextualdemo.di

import com.app.contextualdemo.data.repository.AppKeyValidationModelImpl
import com.app.contextualdemo.data.service.AppService
import com.app.contextualdemo.domain.repository.AppKeyValidationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesAppValidationRepository(appService: AppService): AppKeyValidationRepository {
        return AppKeyValidationModelImpl(appService)
    }
}