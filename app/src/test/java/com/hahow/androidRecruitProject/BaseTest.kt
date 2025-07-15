package com.hahow.androidRecruitProject

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseTest {

    private lateinit var testApplication: TestApplication
    internal val testDispatcher = StandardTestDispatcher()

    @Before
    open fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        mockkObject(MyApplication)
        mockkObject(MyApplication.Companion)
        testApplication = mockk(relaxed = true)
        every { MyApplication.appContext } returns testApplication.applicationContext
    }


    @After
    open fun tearDown() {
        Dispatchers.resetMain()
    }

}