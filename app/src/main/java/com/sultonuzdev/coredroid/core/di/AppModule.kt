package com.sultonuzdev.coredroid.core.di

import com.sultonuzdev.coredroid.core.utils.FileExporter
import com.sultonuzdev.coredroid.core.utils.PermissionManager
import com.sultonuzdev.coredroid.data.local.preferences.PreferencesManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { PermissionManager(get()) }
    single { FileExporter(get()) }
    single { PreferencesManager(get()) }
}
