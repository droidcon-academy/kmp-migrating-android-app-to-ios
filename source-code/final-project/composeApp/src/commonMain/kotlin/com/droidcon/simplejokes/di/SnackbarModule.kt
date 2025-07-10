package com.droidcon.simplejokes.di

import com.droidcon.simplejokes.core.presentation.SnackbarManager
import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module

fun snackbarModule(coroutineScope: CoroutineScope) = module {

    single { SnackbarManager(coroutineScope) }

}