package com.assignment.todayweather


import com.assignment.todayweather.data.remote.model.Coord
import com.assignment.todayweather.di.mModules
import com.assignment.todayweather.domain.interactors.*
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
    private val searchCityByLocationUseCase: SearchCityByLocationUseCase by inject()
    private val getDailyUseCase: GetDailyUseCase by inject()

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

    @Test
    fun testSearchCityByLocation() = runTest {
        val param = SearchCityByLocationParams(
            location = Coord(lat = 13.75, lon = 100.5167), units = "metric"
        )
        searchCityByLocationUseCase.execute(param).collectLatest {
            assertEquals(it.sys.country, "TH")
        }
    }

    @Test
    fun testGetDaily() = runTest {
        val param = GetDailyParams(
            lat = 13.75, lon = 100.5167, exclude = "daily", units = "metric"
        )
        getDailyUseCase.execute(param).collectLatest {
            assertEquals(it.timezone, "Asia/Bangkok")
        }
    }
}
