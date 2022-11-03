package com.example.number.numbers.presentation

import com.example.number.numbers.domain.NumberFact
import com.example.number.numbers.domain.NumberUiMapper
import com.example.number.numbers.domain.NumbersInteractor
import com.example.number.numbers.domain.NumbersResult
import org.junit.Assert.*
import org.junit.Test

class ViewModelTest: BaseTest() {

    /**
     * Initial test
     * At start fetch data and show it
     * then try to get some data successfully
     * then re-init and check the result
     */
    @Test
    fun `test init and re-init`() {

        val communications = TestNumberCommunication()
        val interactor = TestNumberInteractor()
        //1. init
        val viewModel = NumberViewModel(
            communications,
            interactor,
            NumbersResultMapper(communications, NumberUiMapper())
        )
        interactor.changeExpectedResult(NumbersResult.Success())
        //2. action
        viewModel.init(isFirstRun = true)
        //3/check
        assertEquals(true, communications.progressCalledList[0])
        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success(), communications.stateCalledList[0])

        assertEquals(0, communications.numbersList.size)
        assertEquals(0, communications.timesShowList)

        //get some data
        interactor.changeExpectedResult(NumbersResult.Failure("no internet connection"))
        viewModel.fetchRandomNumberFact()

        assertEquals(true, communications.progressCalledList[2])

        assertEquals(1, interactor.fetchAboutRandomNumberCalledList.size)

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[3])

        assertEquals(2, communications.stateCalledList.size)
        assertEquals(UiState.Error("no internet connection"), communications.stateCalledList[1])
        assertEquals(1, communications.timesShowList)

        viewModel.init(isFirstRun = false)
        assertEquals(4, communications.progressCalledList.size)
        assertEquals(2, communications.stateCalledList.size)
        assertEquals(1, communications.timesShowList)
    }

    /**
     * Try to get information  about empty number
     */
    @Test
    fun `fact about empty number`() {
        val communications = TestNumberCommunication()
        val interactor = TestNumberInteractor()

        val viewModel =
            NumberViewModel(
                communications,
                interactor,
                NumbersResultMapper(communications, NumberUiMapper())
            )

        viewModel.fetchNumberFact("")

        assertEquals(0, interactor.fetchAboutNumberCalledList.size)

        assertEquals(0, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[0])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Error("enter number is empty"), communications.stateCalledList[0])

        assertEquals(0, communications.timesShowList)
    }

    /**
     * Try to get information  about some number
     */
    @Test
    fun `fact about some number`() {
        val communications = TestNumberCommunication()
        val interactor = TestNumberInteractor()

        val viewModel =
            NumberViewModel(
                communications,
                interactor,
                NumbersResultMapper(communications, NumberUiMapper())
            )

        interactor.changeExpectedResult(
            NumbersResult.Success(
                listOf(
                    NumberFact(
                        "45",
                        "random fact about 45"
                    )
                )
            )
        )

        viewModel.fetchNumberFact("45")

        assertEquals(1, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[0])

        assertEquals(1, interactor.fetchAboutNumberCalledList.size)
        assertEquals(
            NumberFact("45", "random fact about 45"),
            interactor.fetchAboutNumberCalledList[0]
        )

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[0])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success(), communications.stateCalledList[0])

        assertEquals(1, communications.timesShowList)
        assertEquals(NumberUi("45", "random fact about 45"), communications.numbersList[0])
    }




    private class TestNumberInteractor : NumbersInteractor {

        private var result: NumbersResult = NumbersResult.Success()

        val initCalledList = mutableListOf<NumbersResult>()

        val fetchAboutNumberCalledList = mutableListOf<NumbersResult>()
        val fetchAboutRandomNumberCalledList = mutableListOf<NumbersResult>()

        fun changeExpectedResult(newResult: NumbersResult) {
            result = newResult
        }


        override suspend fun init(): NumbersResult {
            initCalledList.add(result)
            return result
        }

        override suspend fun factAboutNumber(number: String): NumbersResult {

            fetchAboutNumberCalledList.add(result)
            return result
        }

        override suspend fun factAboutRandomNumber(): NumbersResult {
            fetchAboutRandomNumberCalledList.add(result)
            return result
        }
    }
}