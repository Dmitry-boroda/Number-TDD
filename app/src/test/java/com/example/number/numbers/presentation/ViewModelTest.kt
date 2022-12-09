package com.example.number.numbers.presentation

import android.view.View
import com.example.number.numbers.domain.NumberFact
import com.example.number.numbers.domain.NumberUiMapper
import com.example.number.numbers.domain.NumbersInteractor
import com.example.number.numbers.domain.NumbersResult
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ViewModelTest : BaseTest() {

    private lateinit var viewModel: NumberViewModel
    private lateinit var communications: TestNumberCommunication
    private lateinit var interactor: TestNumberInteractor
    private lateinit var managerResources: TestManagerResources
    private lateinit var testDispatcherList: TestDispatcherList

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI tread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun init() {

        Dispatchers.setMain(mainThreadSurrogate)

        communications = TestNumberCommunication()
        interactor = TestNumberInteractor()
        managerResources = TestManagerResources()
        testDispatcherList = TestDispatcherList()
        //1. init
        viewModel = NumberViewModel(
            HandelNumbersRequest.Base(
                TestDispatcherList(),
                communications,
                NumbersResultMapper(communications, NumberUiMapper())
            ),
            managerResources,
            communications,
            interactor,
        )

    }

    /**
     * Initial test
     * At start fetch data and show it
     * then try to get some data successfully
     * then re-init and check the result
     */
    @Test
    fun `test init and re-init`() = runBlocking {


        interactor.changeExpectedResult(NumbersResult.Success())
        //2. action
        viewModel.init(isFirstRun = true)
        //3/check
        assertEquals(View.VISIBLE, communications.progressCalledList[0])
        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(true, communications.stateCalledList[0] is UiState.Success)

        assertEquals(0, communications.numbersList.size)
        assertEquals(0, communications.timesShowList)

        //get some data
        interactor.changeExpectedResult(NumbersResult.Failure("no internet connection"))
        viewModel.fetchRandomNumberFact()

        assertEquals(View.VISIBLE, communications.progressCalledList[2])

        assertEquals(1, interactor.fetchAboutRandomNumberCalledList.size)

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[3])

        assertEquals(2, communications.stateCalledList.size)
        assertEquals(UiState.ShowError("no internet connection"), communications.stateCalledList[1])
        assertEquals(0, communications.timesShowList)

        viewModel.init(isFirstRun = false)
        assertEquals(4, communications.progressCalledList.size)
        assertEquals(2, communications.stateCalledList.size)
        assertEquals(0, communications.timesShowList)
    }

    /**
     * Try to get information  about empty number
     */
    @Test
    fun `fact about empty number`() = runBlocking {
        managerResources.makeExpectedAnswer("enter number is empty")
        viewModel.fetchNumberFact("")


        assertEquals(0, interactor.fetchAboutNumberCalledList.size)

        assertEquals(0, communications.progressCalledList.size)

        assertEquals(1, communications.stateCalledList.size)

        assertEquals(UiState.ShowError("enter number is empty"), communications.stateCalledList[0])

        assertEquals(0, communications.timesShowList)
    }

    /**
     * Try to get information  about some number
     */
    @Test
    fun `fact about some number`() = runBlocking {
        interactor.changeExpectedResult(
            NumbersResult.Success(listOf(NumberFact("45", "fact about 45")))
        )

        viewModel.fetchNumberFact("45")

        assertEquals(View.VISIBLE, communications.progressCalledList[0])

        assertEquals(1, interactor.fetchAboutNumberCalledList.size)
        assertEquals(
            NumbersResult.Success(listOf(NumberFact("45", "fact about 45"))),
            interactor.fetchAboutNumberCalledList[0]
        )

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(true, communications.stateCalledList[0] is UiState.Success)

        assertEquals(1, communications.timesShowList)
        assertEquals(NumberUi("45", "fact about 45"), communications.numbersList[0])
    }

    @Test
    fun `test cleaner error`() {
        viewModel.clearError()

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(true, communications.stateCalledList[0] is UiState.ClearError)
    }


    private class TestManagerResources : ManagerResources {

        private var string: String = ""

        fun makeExpectedAnswer(expected: String) {
            string = expected
        }

        override fun string(id: Int): String {
            return string
        }

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

    private class TestDispatcherList : DispatchersList {
        @OptIn(ExperimentalCoroutinesApi::class)
        override fun io(): CoroutineDispatcher {
            return TestCoroutineDispatcher()
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        override fun ui(): CoroutineDispatcher {
            return TestCoroutineDispatcher()
        }

    }
}