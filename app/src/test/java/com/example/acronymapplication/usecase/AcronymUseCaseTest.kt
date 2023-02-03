package com.example.acronymapplication.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.acronymapplication.datamodels.AcronymMeaningsListData
import com.example.acronymapplication.repo.AcronymRepository
import com.example.acronymapplication.repo.DataResponse
import com.example.acronymapplication.usecases.AcronymUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class AcronymUseCaseTest {

    private val mockRepo = mockk<AcronymRepository>()
    private val target = AcronymUseCase(mockRepo)

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun `test get acronyms with empty response`() = runTest {
        coEvery { mockRepo.getAcronymMeaningsList(any(), any()) } returns DataResponse.Success(
            emptyList()
        )

        target.getAcronymMeaningsUseCase("", "").collect {
            assert(it is DataResponse.Success)
            assert((it as DataResponse.Success).data.isEmpty())
        }
    }

    @Test
    fun `test get acronyms with longforms`() = runTest {
        coEvery { mockRepo.getAcronymMeaningsList(any(), any()) } returns DataResponse.Success(
            listOf(AcronymMeaningsListData("Sample Acronym"))
        )

        target.getAcronymMeaningsUseCase("Sample").collect {
            assert(it is DataResponse.Success)
            assert((it as DataResponse.Success).data.size == 1)
            assert(it.data[0].longForms == "Sample Acronym")
        }
    }
}
