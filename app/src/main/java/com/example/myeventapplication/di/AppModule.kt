package com.example.myeventapplication.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.example.myeventapplication.viewModel.MainViewModel
import com.example.myeventapplication.network.ApiService
import com.example.myeventapplication.network.Routing
import com.example.myeventapplication.pref.SettingPreferences
import com.example.myeventapplication.repo.MainRepo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        val longIterator = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(longIterator).build()
        val retrofit = Retrofit.Builder().baseUrl(Routing.MAIN_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()

        retrofit.create(ApiService::class.java)
    }
}

val repoModule = module {
    single { MainRepo(get()) }
}


val prefModule = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = { androidContext().filesDir.resolve("settings.preferences_pb") }
        )
    }

    single { SettingPreferences(get()) }
}
val viewModelModule = module {
    viewModel { MainViewModel(get(), get(), get()) }
}