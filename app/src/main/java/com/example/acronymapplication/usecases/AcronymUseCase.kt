package com.example.acronymapplication.usecases

import com.example.acronymapplication.repo.AcronymRepository
import com.example.acronymapplication.repo.DataResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Use Case class for fetching acronym meanings
 */
class AcronymUseCase(private val acronymRepository: AcronymRepository) {
    suspend fun getAcronymMeaningsUseCase(sf: String, lf: String = ""): Flow<DataResponse> =
        flow {
            emit(acronymRepository.getAcronymMeaningsList(sf, lf))
        }
}
