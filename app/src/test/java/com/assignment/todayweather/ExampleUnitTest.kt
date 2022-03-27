package com.assignment.todayweather


import com.assignment.todayweather.di.mModules
import com.assignment.todayweather.domain.interactors.SearchCityParams
import com.assignment.todayweather.domain.interactors.SearchCityUseCase
import com.assignment.todayweather.data.remote.model.UiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ExampleUnitTest : KoinTest {
    private val searchCityUseCase: SearchCityUseCase by inject()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin {
            modules(mModules)
        }
    }

    @Test
    fun testSearchCity() = runTest {
        val param = SearchCityParams(
            name = "bangkok", units = ""
        )
        searchCityUseCase.execute(param).collectLatest {
            assertEquals(it.name.lowercase(), param.name)
        }
    }
}
