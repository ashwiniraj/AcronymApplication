package com.example.acronymapplication.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * AcronymService class, which creates retrofit instance to handle network request
 */
object AcronymService {

    private const val BASE_URL = "http://www.nactem.ac.uk/"

    private fun getRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getAcronymService(): AcronymAPI = getRetrofitInstance().create(AcronymAPI::class.java)
}
