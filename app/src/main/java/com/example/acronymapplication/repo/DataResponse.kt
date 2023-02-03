package com.example.acronymapplication.repo

import com.example.acronymapplication.datamodels.AcronymMeaningsListData

sealed class DataResponse {
    class Success(val data: List<AcronymMeaningsListData>) : DataResponse()
    class Failure(val message: String) : DataResponse()
}
