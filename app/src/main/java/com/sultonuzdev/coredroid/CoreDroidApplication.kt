package com.sultonuzdev.coredroid

import android.app.Application
import com.sultonuzdev.coredroid.core.di.appModule
import com.sultonuzdev.coredroid.core.di.dataModule
import com.sultonuzdev.coredroid.core.di.domainModule
import com.sultonuzdev.coredroid.core.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class CoreDroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@CoreDroidApplication)

            modules(
                appModule,
                dataModule,
                domainModule,
                presentationModule,
            )

        }


    }
}