package com.assignment.todayweather

import android.app.Application
import com.assignment.todayweather.di.mModules
import com.google.android.material.color.DynamicColors
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

@Suppress("unused")
class TodayWeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
        startKoin {
            androidContext(this@TodayWeatherApp)
            modules(mModules)
        }
        val builder = Picasso.Builder(this)
        val built = builder.build()
        Picasso.setSingletonInstance(built)


    }
}
