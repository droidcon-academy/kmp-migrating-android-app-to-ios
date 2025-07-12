package com.droidcon.simplejokes.di

import com.droidcon.simplejokes.core.presentation.Localization
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val localizationModule: Module
    get() = module {
        single<Localization> {
            Localization(androidApplication())
        }
    }