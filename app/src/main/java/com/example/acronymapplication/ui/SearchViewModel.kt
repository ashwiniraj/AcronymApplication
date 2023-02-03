package com.example.acronymapplication.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acronymapplication.datamodels.AcronymMeaningsListData
import com.example.acronymapplication.deps.UsecaseFactory
import com.example.acronymapplication.repo.DataResponse
import com.example.acronymapplication.repo.DataResponseConstants
import com.example.acronymapplication.usecases.AcronymUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A ViewModel class for SearchActivity
 */
class SearchViewModel(
    private val acronymUseCase: AcronymUseCase = UsecaseFactory.getAcronymUsecase(),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val meaningsMutableLiveData = MutableLiveData<List<AcronymMeaningsListData>>()
    val meaningsLiveData = meaningsMutableLiveData as LiveData<List<AcronymMeaningsListData>>

    private val errorMessageMutableLiveData = MutableLiveData<String>()
    val errorMessageLiveData = errorMessageMutableLiveData as LiveData<String>

    private val stateMutableLiveData = MutableLiveData<Boolean>()
    val stateLiveData: LiveData<Boolean> = stateMutableLiveData

    @VisibleForTesting
    var acronymText: String = ""

    /**
     * This method is responsible to fetch the Long Forms for given acronym
     */
    fun onSearchClicked() {
        viewModelScope.launch(ioDispatcher) {
            try {
                stateMutableLiveData.postValue(true)
                val result = acronymUseCase.getAcronymMeaningsUseCase(sf = acronymText)
                result.collect {
                    when (it) {
                        is DataResponse.Success -> {
                            stateMutableLiveData.postValue(false)
                            meaningsMutableLiveData.postValue(it.data)
                        }
                        is DataResponse.Failure -> {
                            stateMutableLiveData.postValue(false)
                            errorMessageMutableLiveData.postValue(it.message)
                        }
                    }
                }
            } catch (e: Exception){
                stateMutableLiveData.postValue(false)
                errorMessageMutableLiveData.postValue(DataResponseConstants.ERROR_RESULTS)
            }
        }
    }
}
