package com.unero.githubuser.di

import android.app.Application
import androidx.room.Room
import com.unero.githubuser.data.Repository
import com.unero.githubuser.data.RepositoryImpl
import com.unero.githubuser.data.local.LocalDataSource
import com.unero.githubuser.data.local.room.FavoriteDao
import com.unero.githubuser.data.local.room.FavoriteDatabase
import com.unero.githubuser.data.remote.Api
import com.unero.githubuser.ui.fragments.detail.DetailViewModel
import com.unero.githubuser.ui.fragments.favorite.FavoriteViewModel
import com.unero.githubuser.ui.fragments.home.HomeViewModel
import com.unero.githubuser.ui.widget.StackRemoteViewsFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    fun client(): Api {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(Api::class.java)
    }
    single { client() }
}

val roomModule = module {
    fun provideDatabase(application: Application): FavoriteDatabase {
        return Room.databaseBuilder(application, FavoriteDatabase::class.java, "favDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(database: FavoriteDatabase): FavoriteDao {
        return database.dao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
    single { StackRemoteViewsFactory(androidApplication(), get()) }
}

val repositoryModule = module {
    fun repository(api: Api, local: LocalDataSource): Repository {
        return RepositoryImpl(api, local)
    }

    single { LocalDataSource(get()) }
    single { repository(get(), get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}