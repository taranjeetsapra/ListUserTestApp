package com.example.listusertestapp

import com.example.listusertestapp.data.model.UserInfo
import com.example.listusertestapp.data.service.api.UserInfoRepo
import com.example.listusertestapp.domain.repository.ResultState
import com.example.listusertestapp.presentation.UserViewModel
import com.example.listusertestapp.presentation.sealed.ResourceState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

/**
 * Created by Taranjeet Singh on 16/10/25.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    private val userRepository: UserInfoRepo = mock()
    private lateinit var viewModel: UserViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    private val mockUser = UserInfo(
        id = 1,
        name = "Cary Mueller5",
        company = "Thiel - Quigley",
        username = "Mossie.Kunde42",
        email = "Myrna.Raynor76@gmail.com",
        address = "8713 Hillard Land",
        zip = "30995",
        state = "Maine",
        country = "Argentina",
        phone = "896.502.2683",
        photo = "https://json-server.dev/ai-profiles/66.png"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // Set Main dispatcher for coroutines
        viewModel = UserViewModel(userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset Main dispatcher
    }

    @Test
    fun `initial state should be loading`() = runTest {
        // Since ViewModel calls fetchUser(1) in init, we must mock the initial call
        whenever(userRepository.getUserInfo()).thenReturn(ResultState.Success(listOf(mockUser)))

        // Initial state is checked before any coroutine dispatch completes fully
        val initialState = viewModel.state.value
        // ASSERT: The state should be the Loading object
        assertEquals(ResourceState.Loading, initialState)
    }


    @Test
    fun `successful fetch should transition state to Success with data`() = runTest {
        // ARRANGE: Mock the repository call to return a success result
        whenever(userRepository.getUserInfo()).thenReturn(
            ResultState.Success(listOf(mockUser))
        )

        // ACT: Allow all coroutines (including the one in the init block) to complete
        advanceUntilIdle()

        // ASSERT: The final state should be Success, containing the mock list
        val finalState = viewModel.state.value

        // 1. Check if the type is Success
        assert(finalState is ResourceState.Success)

        // 2. Safely cast and check the contained data
        val successState: ResourceState.Success<List<UserInfo>> = finalState as ResourceState.Success
        assertEquals(listOf(mockUser), successState.dataVal)
    }


    @Test
    fun `failed fetch should transition state to Error`() = runTest {
        // ARRANGE: Mock the repository call to return a failure
        val errorMessage = "API Unavailable"
        val exception = Exception(errorMessage)

        whenever(userRepository.getUserInfo()).thenReturn(
            ResultState.Failure(exception)
        )

        // ACT: Allow all coroutines to complete
        advanceUntilIdle()

        // ASSERT: The final state should be Error, containing the message
        val finalState = viewModel.state.value

        // 1. Check if the type is Error
        assert(finalState is ResourceState.Error)

        // 2. Safely cast and check the contained message
        val errorState = finalState as ResourceState.Error
        assertEquals(errorMessage, errorState.msg)
    }

}