package com.droidcon.cmp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.droidcon.cmp.core.domain.datasource.PreferencesDataSource
import com.droidcon.cmp.core.presentation.Localization
import com.droidcon.cmp.core.presentation.utils.SetSystemBarAppearance
import com.droidcon.cmp.di.platformModule
import com.droidcon.cmp.di.sharedModule
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
        val preferencesDataSource = koinInject<PreferencesDataSource>()
        val localization = koinInject<Localization>()

        val themePreference by preferencesDataSource.getTheme().collectAsStateWithLifecycle("")
        val systemIsDark = isSystemInDarkTheme() // Check system's dark theme preference

        // Determine whether to use dark theme based on preference or system setting
        val useDarkTheme = remember(themePreference, systemIsDark) {
            when (themePreference.uppercase()) { // Normalize for "dark", "Dark", "DARK"
                "DARK" -> true
                "LIGHT" -> false
                else -> systemIsDark // Fallback to system theme
            }
        }

        MaterialTheme(colorScheme = if (useDarkTheme) darkColorScheme() else lightColorScheme()) {
            SetSystemBarAppearance(darkTheme = useDarkTheme)

            LaunchedEffect(Unit) {
                preferencesDataSource.getLanguage()
                    .distinctUntilChanged()
                    .collect { savedLanguageTag ->
                        localization.updateLocale(savedLanguageTag)
                    }
            }

            NavigationRoot()

        }
    }
}