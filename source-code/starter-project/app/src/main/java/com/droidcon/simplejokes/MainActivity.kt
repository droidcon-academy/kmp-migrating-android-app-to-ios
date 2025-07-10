package com.droidcon.simplejokes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
import com.droidcon.simplejokes.core.presentation.SetSystemBarAppearance
import com.droidcon.simplejokes.core.presentation.SnackbarManager
import com.droidcon.simplejokes.di.databaseModule
import com.droidcon.simplejokes.di.jokesRepositoryModule
import com.droidcon.simplejokes.di.localizationModule
import com.droidcon.simplejokes.di.networkModule
import com.droidcon.simplejokes.di.preferencesModule
import com.droidcon.simplejokes.di.snackbarModule
import com.droidcon.simplejokes.di.vaultModule
import com.droidcon.simplejokes.di.viewModelsModule
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

class MainActivity : AppCompatActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KoinApplication(application = {
                androidContext(this@MainActivity.applicationContext)
                modules(
                    databaseModule,
                    jokesRepositoryModule,
                    localizationModule,
                    networkModule,
                    preferencesModule,
                    snackbarModule,
                    vaultModule,
                    viewModelsModule
                )
            }) {
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
