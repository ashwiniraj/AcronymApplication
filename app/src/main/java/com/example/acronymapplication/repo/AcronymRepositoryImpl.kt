package com.example.acronymapplication.repo

import com.example.acronymapplication.datamodels.AcronymMeaningsListData
import com.example.acronymapplication.network.AcronymAPI

/**
 * AcronymRepositoryImpl implements AcronymRepository interface, which handles data from different
 * data sources
 */
class AcronymRepositoryImpl(private val acronymAPI: AcronymAPI) : AcronymRepository {

    override suspend fun getAcronymMeaningsList(sf: String, lf: String): DataResponse = run {
        try {
            val response = acronymAPI.getAcronymMeanings(sf, lf)
            if (response.isSuccessful) {
                val dataResponse = response.body()?.let {
                    if (it.isEmpty()) {
                        DataResponse.Failure(DataResponseConstants.NO_RESULTS_FOUND)
                    } else {
                        val list = mutableListOf<AcronymMeaningsListData>()
                        it.forEach { acronymResponse ->
                            list.addAll(acronymResponse.lfs.map { longForms ->
                                AcronymMeaningsListData(
                                    longForms.lf
                                )
                            })
                        }
                        DataResponse.Success(list)
                    }
                } ?: DataResponse.Failure(DataResponseConstants.NO_RESULTS_FOUND)
                dataResponse
            } else {
                DataResponse.Failure(DataResponseConstants.ERROR_RESULTS)
            }
        } catch (e: Exception) {
            DataResponse.Failure(DataResponseConstants.ERROR_RESULTS)
        }
    }
}
