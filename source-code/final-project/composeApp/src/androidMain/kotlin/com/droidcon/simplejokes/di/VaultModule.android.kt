package com.droidcon.simplejokes.di

import com.droidcon.simplejokes.core.data.Vault
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val vaultModule: Module
    get() = module {
        single<Vault> {
            Vault(androidApplication())
        }
    }