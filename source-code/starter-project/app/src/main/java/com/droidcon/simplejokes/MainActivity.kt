package com.droidcon.simplejokes

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.droidcon.simplejokes.core.domain.datasource.PreferencesDataSource
import com.droidcon.simplejokes.core.presentation.Localization
import com.droidcon.simplejokes.ui.theme.SimpleJokesTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var preferencesDataSource: PreferencesDataSource

    @Inject
    lateinit var localization: Localization


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val themePreference by preferencesDataSource.getTheme().collectAsStateWithLifecycle("")
            val useDarkTheme = when (themePreference) {
                "DARK" -> true
                "LIGHT" -> false
                else -> isSystemInDarkTheme() // Default to system theme
            }



            val view = LocalView.current
            if (!view.isInEditMode) { // Good practice for Composable previews
                LaunchedEffect(useDarkTheme) {
                    val window = (view.context as? Activity)?.window ?: this@MainActivity.window
                    WindowInsetsControllerCompat(window, view).let { controller ->
                        controller.isAppearanceLightStatusBars = !useDarkTheme
                        controller.isAppearanceLightNavigationBars = !useDarkTheme
                    }
                }
            }

            LaunchedEffect(Unit) {
                preferencesDataSource.getLanguage()
                    .distinctUntilChanged()
                    .collect { savedLanguageTag ->
                        localization.updateLocale(savedLanguageTag)
                    }
            }

            SimpleJokesTheme(darkTheme = useDarkTheme) {
                NavigationRoot()
            }
        }
    }
}
