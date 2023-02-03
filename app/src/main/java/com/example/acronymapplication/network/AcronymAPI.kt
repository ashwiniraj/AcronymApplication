package com.example.acronymapplication.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AcronymAPI {
    @GET("/software/acromine/dictionary.py")
    suspend fun getAcronymMeanings(
        @Query("sf") sf: String,
        @Query("lf") lf: String
    ): Response<List<AcronymResponse>>
}
