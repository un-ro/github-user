package com.unero.githubuser

import android.app.Application
import com.unero.githubuser.di.apiModule
import com.unero.githubuser.di.repositoryModule
import com.unero.githubuser.di.roomModule
import com.unero.githubuser.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(
                apiModule,
                roomModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}