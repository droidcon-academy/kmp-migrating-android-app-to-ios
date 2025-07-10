package com.droidcon.simplejokes

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.droidcon.simplejokes.core.domain.datasource.PreferencesDataSource
import com.droidcon.simplejokes.core.presentation.Localization
import com.droidcon.simplejokes.core.presentation.utils.SetSystemBarAppearance
import com.droidcon.simplejokes.di.databaseModule
import com.droidcon.simplejokes.di.jokesRepositoryModule
import com.droidcon.simplejokes.di.localizationModule
import com.droidcon.simplejokes.di.networkModule
import com.droidcon.simplejokes.di.preferencesModule
import com.droidcon.simplejokes.di.vaultModule
import com.droidcon.simplejokes.di.viewModelsModule
import com.droidcon.simplejokes.ui.theme.SimpleJokesTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

class MainActivity : AppCompatActivity() {

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
                    vaultModule,
                    viewModelsModule
                )
            }) {
                val preferencesDataSource = koinInject<PreferencesDataSource>()
                val localization = koinInject<Localization>()

                val themePreference by preferencesDataSource.getTheme()
                    .collectAsStateWithLifecycle("")
                val useDarkTheme = when (themePreference) {
                    "DARK" -> true
                    "LIGHT" -> false
                    else -> isSystemInDarkTheme() // Default to system theme
                }

                SimpleJokesTheme(darkTheme = useDarkTheme) {
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
    }
}
