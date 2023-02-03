package com.example.acronymapplication.deps

import com.example.acronymapplication.network.AcronymService.getAcronymService
import com.example.acronymapplication.repo.AcronymRepositoryImpl
import com.example.acronymapplication.usecases.AcronymUseCase

/**
 * Factory class that provides Retrofit instance
 */
object UsecaseFactory {
    fun getAcronymUsecase() = AcronymUseCase(AcronymRepositoryImpl(getAcronymService()))
}
