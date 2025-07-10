package com.droidcon.simplejokes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.droidcon.simplejokes.core.domain.datasource.PreferencesDataSource
import com.droidcon.simplejokes.core.presentation.Localization
import com.droidcon.simplejokes.core.presentation.SnackbarManager
import com.droidcon.simplejokes.core.presentation.utils.SetSystemBarAppearance
import com.droidcon.simplejokes.di.platformModule
import com.droidcon.simplejokes.di.sharedModule
import com.droidcon.simplejokes.di.snackbarModule
import kotlinx.coroutines.flow.distinctUntilChanged
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinMultiplatformApplication
import org.koin.compose.koinInject
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration


@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {
    KoinMultiplatformApplication(
        config = KoinConfiguration {
            modules(sharedModule, platformModule)
        }
    ) {
        JokesAppTheme {
            AppEffectHost { snackbarHostState ->
                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) {
                    NavigationRoot()
                }
            }
        }
    }
}

@Composable
private fun JokesAppTheme(content: @Composable () -> Unit) {
    val preferencesDataSource = koinInject<PreferencesDataSource>()
    val themePreference by preferencesDataSource.getTheme().collectAsStateWithLifecycle("")
    val systemIsDark = isSystemInDarkTheme()

    val useDarkTheme = remember(themePreference, systemIsDark) {
        when (themePreference.uppercase()) {
            "DARK" -> true
            "LIGHT" -> false
            else -> systemIsDark
        }
    }

    MaterialTheme(colorScheme = if (useDarkTheme) darkColorScheme() else lightColorScheme()) {
        SetSystemBarAppearance(darkTheme = useDarkTheme)
        content() // Render the rest of the app inside the theme
    }
}

@Composable
private fun AppEffectHost(content: @Composable (snackbarHostState: SnackbarHostState) -> Unit) {
    val localization = koinInject<Localization>()
    val preferencesDataSource = koinInject<PreferencesDataSource>()
    val snackbarManager = koinInject<SnackbarManager>()

    val snackbarHostState = remember { SnackbarHostState() }

    // Effect for handling language changes
    LaunchedEffect(localization, preferencesDataSource) {
        preferencesDataSource.getLanguage()
            .distinctUntilChanged()
            .collect { savedLanguageTag ->
                localization.updateLocale(savedLanguageTag)
            }
    }

    // Effect for handling snackbar messages
    LaunchedEffect(snackbarManager) {
        snackbarManager.messages.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    // Render the content, passing down the snackbar state
    content(snackbarHostState)
}