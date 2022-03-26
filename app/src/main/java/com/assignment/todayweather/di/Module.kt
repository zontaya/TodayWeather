package com.assignment.todayweather.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.assignment.todayweather.repository.RepositoryImp
import com.assignment.todayweather.domain.IRemoteData
import com.assignment.todayweather.domain.IRepository
import com.assignment.todayweather.domain.interactors.SearchCityUseCase
import com.assignment.todayweather.data.remote.RemoteDataImp
import com.assignment.todayweather.domain.interactors.GetDailyUseCase
import com.assignment.todayweather.repository.model.mapper.ApiUiMapper
import com.assignment.todayweather.ui.DetailViewModel
import com.assignment.todayweather.ui.MainViewModel
import com.assignment.todayweather.util.Constants.API_KEY
import com.assignment.todayweather.util.Constants.BASE_URL
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }

}
private val useCaseModule = module {
    factory { SearchCityUseCase(get()) }
    factory { GetDailyUseCase(get()) }
}
private val mapModule = module {
    factory { ApiUiMapper() }
}
private val commonModule = module {
    single<IRepository> { RepositoryImp(get(), get()) }
    single<IRemoteData> { RemoteDataImp(get()) }
    single<Retrofit> {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("apikey", API_KEY)
                .build()
            val requestBuilder = original.newBuilder()
                .url(url)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
        okHttpClientBuilder.build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }
    single { BASE_URL }


}
val mModules = listOf(commonModule, mapModule, useCaseModule, viewModelModule)
