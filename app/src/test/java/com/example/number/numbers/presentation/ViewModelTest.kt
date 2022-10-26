package com.example.number.numbers.presentation

import com.example.number.numbers.domain.NumbersResult
import org.junit.Assert.*
import org.junit.Test
import java.util.ArrayList

class ViewModelTest{

    /**
     * Initial test
     * At start fetch data and show it
     * then try to get some data successfully
     * then re-init and check the result
     */
    @Test
    fun `test init and re-init`(){

        val communications =TestNumberCommunication()
        val interactor = TestNumberInteractor()
        //1. init
        val viewModel = NumberViewModel(communications, interactor)
        interactor.changeExpectedResult(NumberResult.Success())
        //2. action
        viewModel.init(isFirstRun = true)
        //3/check
        assertEquals(1, communications.progressCallList.size)
        assertEquals(true, communications.progressCallList[0])

        assertEquals(2, communications.progressCallList.size)
        assertEquals(false, communications.progressCallList[0])

        assertEquals(1, communications.stateCallList.size)
        assertEquals(UiState.Success(emptyList<NumberUi>()), communications.stateCallList[0])

        assertEquals(0,communications.numbersList.size)
        assertEquals(0,communications.timesShowList)



        interactor.changeExpectedResult(NumberResult.Failure())
        viewModel.fetchRandomNumberDate()


        assertEquals(3,communications.progressCallList.size)
        assertEquals(true,communications.progressCallList[2])

        assertEquals(1,interactor.fetchAboutRandomNumberCalledList.size)

        assertEquals(4,communications.progressCallList.size)
        assertEquals(false,communications.progressCallList[3])

        assertEquals(2,communications.stateCallList.size)
        assertEquals(UiState.Error(/* todo message */), communications.stateCallList[1])
        assertEquals(1,communications.timesShowList)

        viewModel.init(isFirstRun = false)
        assertEquals(4,communications.progressCallList.size)
        assertEquals(2,communications.stateCallList.size)
        assertEquals(1,communications.timesShowList)
    }

    /**
     * Try to get information  about empty number
     */
    @Test
    fun `fact about empty number`(){
        val communications =TestNumberCommunication()
        val interactor = TestNumberInteractor()

        val viewModel = NumberViewModel(communications, interactor)

        viewModel.fechFact("")

        assertEquals(0,interactor.fetchAboutNumberCalledList.size)

        assertEquals(0, communications.progressCallList.size)
        assertEquals(false, communications.progressCallList[0])

        assertEquals(1, communications.stateCallList.size)
        assertEquals(UiState.Error("enter number is empty"), communications.stateCallList[0])

        assertEquals(0,communications.timesShowList)
    }
    /**
     * Try to get information  about some number
     */
    @Test
    fun `fact about some number`(){
        val communications =TestNumberCommunication()
        val interactor = TestNumberInteractor()

        val viewModel = NumberViewModel(communications, interactor)

        interactor.changeExpectedResult(NumbersResult.Success(listOf(Number("45", "random fact about 45"))))

        viewModel.fechFact("45")

        assertEquals(1, communications.progressCallList.size)
        assertEquals(true, communications.progressCallList[0])

        assertEquals(1,interactor.fetchAboutNumberCalledList.size)
        assertEquals(Number("45", "random fact about 45"),interactor.fetchAboutNumberCalledList[0])

        assertEquals(2, communications.progressCallList.size)
        assertEquals(false, communications.progressCallList[0])

        assertEquals(1, communications.stateCallList.size)
        assertEquals(UiState.Success(), communications.stateCallList[0])

        assertEquals(1,communications.timesShowList)
        assertEquals(NumberUi("45", "random fact about 45"), communications.numbersList[0])
    }


    private class TestNumberCommunication: NumbersCommunications{

    val progressCallList = mutableListOf<Boolean>()
    val stateCallList = mutableListOf<Boolean>()
    var timesShowList = 0
    val numbersList = mutableListOf<NumberUi>()

    override fun showProgress(show: Boolean){
        progressCallList.add(show)
    }
    override fun showState(state: UiState){
        timesShowList++
        stateCallList.add(state)

    }
    override fun showList(list: List<NumberUi>){
        numbersList.addAll(list)
    }
}
    private class TestNumberInteractor: NumberInteractoe{

        private var result: NumbersResult = NumbersResult.Success()

        val initCalledList = mutableListOf<NumbersResult>()

        val fetchAboutNumberCalledList = mutableListOf<NumbersResult>()
        val fetchAboutRandomNumberCalledList = mutableListOf<NumbersResult>()

        fun changeExpectedResult(newResult: NumberResult){
            result = newResult
        }


        override suspend fun init(): NumbersResult{
            initCalledList.add(result)
            return result
        }
        override suspend fun factAboutNumber (number: String): NumbersResult{

            fetchAboutNumberCalledList.add(result)
            return result
        }
        override suspend fun factAboutRandomNumber (): NumberResult{
            fetchAboutRandomNumberCalledList.add(result)
            return result
        }
    }
}