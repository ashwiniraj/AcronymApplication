package com.example.acronymapplication.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.acronymapplication.datamodels.AcronymMeaningsListData
import com.example.acronymapplication.repo.DataResponse
import com.example.acronymapplication.repo.DataResponseConstants
import com.example.acronymapplication.usecases.AcronymUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    private lateinit var searchViewModel: SearchViewModel
    private val acronymUseCase: AcronymUseCase = mockk(relaxed = true)
    private val dispatcher = UnconfinedTestDispatcher()

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        searchViewModel = SearchViewModel(acronymUseCase, dispatcher)
    }

    @Test
    fun getAcronymMeaningsSuccessTest() = runTest(dispatcher) {

        searchViewModel.acronymText = "USD"

        val mockFlow =
            MutableStateFlow(DataResponse.Success(listOf(AcronymMeaningsListData("U.S Dollars"))))
        coEvery { acronymUseCase.getAcronymMeaningsUseCase(any(), any()) } returns mockFlow

        searchViewModel.onSearchClicked()

        assertTrue(searchViewModel.meaningsLiveData.value?.size == 1 )
        assertTrue(searchViewModel.meaningsLiveData.value?.get(0)?.longForms ==  "U.S Dollars")
    }

    @Test
    fun getAcronymMeaningsFailureTest() = runTest(dispatcher) {
        searchViewModel.acronymText = ""

        val mockFlow =
            MutableStateFlow(DataResponse.Failure(DataResponseConstants.NO_RESULTS_FOUND))
        coEvery { acronymUseCase.getAcronymMeaningsUseCase(any(), any()) } returns mockFlow

        searchViewModel.onSearchClicked()

        assertTrue(searchViewModel.errorMessageLiveData.value == DataResponseConstants.NO_RESULTS_FOUND)
    }
}
