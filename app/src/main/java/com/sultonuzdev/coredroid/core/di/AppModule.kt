package com.sultonuzdev.coredroid.core.di

import com.sultonuzdev.coredroid.core.utils.PermissionManager
import org.koin.dsl.module

val appModule = module {
    single { PermissionManager(get()) }
}
