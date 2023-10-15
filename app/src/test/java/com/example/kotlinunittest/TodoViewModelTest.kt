package com.example.kotlinunittest

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinunittest.localdb.AppDatabase
import com.example.kotlinunittest.localdb.TodoDataSource
import com.example.kotlinunittest.localdb.TodoEntity
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TodoViewModelTest : TestCase() {

    @get:Rule
    val instanceTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    private val onTodoSuccessObserver =
        mockk<LiveData<TodoEntity>>()
    private val onTodoResult = mutableListOf<MutableLiveData<TodoEntity>>()


    private lateinit var vm: TodoViewModel

    @Before
    public override fun setUp() {
        initials()
    }

    @After
    public override fun tearDown() {
        AppDatabase.close()
    }

    private fun initials() {
        val mContextMock = mockk<Context>(relaxed = true)
        val db = AppDatabase.buildDB(mContextMock)
        val ds = TodoDataSource(db.todoDao)

        vm = TodoViewModel(ds)
    }

//    @Test
//    fun `whenNull`() = runBlocking{
////        val valid = mockk<Validator>()
////        coEvery { valid.telephoneNumberValidator("") } returns true
//
//        vm.addTodo("asdfas", "sdfas")
//        assertThat("").isEmpty()
//
//    }

    @Test
    fun loadTodoListTest() = runBlocking {
        every { onTodoSuccessObserver.onChanged(any()) } returns Unit
        val data =
            TranCateSubmitConfirmRequestData(
                productId = "1234567890",
                atmWithdrawalLimit = "1000"
            )
        val request = TranCateSubmitConfirmRequest(data)

        val dataResponse = getAtmWithdrawalSubmitResponse()
        val dataModel = dataResponse.response.toCardLimitSubmitConfirmModel()

        val result = Result.Success(dataModel)


        runBlocking {
            coEvery { submitUseCase(request) } returns result
            viewModel.submitTransactionCategoryEvent.observeForever(onSubmitSuccessObserver)
            viewModel.submitTransactionCategoryEventTrigger.postValue(Event(request))

            verify(exactly = 2) {
                onSubmitSuccessObserver.onChanged(
                    capture(
                        onSubmitResult
                    )
                )
            }
            assertEquals(
                onSubmitResult.size,
                2,
                "AtmWithdrawalLimit submission Result Size"
            )
        }
    }
}