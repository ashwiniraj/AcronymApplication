package com.example.acronymapplication.repo

interface AcronymRepository {
    suspend fun getAcronymMeaningsList(sf: String, lf: String): DataResponse
}