package com.droidcon.simplejokes.core.presentation

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SnackbarManager(
    private val coroutineScope: CoroutineScope
) {
    val snackbarHostState = SnackbarHostState()

    fun showMessage(message: String) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }
}