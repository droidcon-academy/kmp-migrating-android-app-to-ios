package com.droidcon.simplejokes.core.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import platform.UIKit.UIApplication
import platform.UIKit.UIStatusBarStyleDarkContent
import platform.UIKit.UIStatusBarStyleLightContent
import platform.UIKit.setStatusBarStyle

@Composable
actual fun SetSystemBarAppearance(darkTheme: Boolean) {
    // by default iOS doesn't need that code as it automatically changes UIStatusBar color
    // based on the StatusBar background dimming.  Keep it in case you want to bypass default behaviour
    // Note: Requires additional configuration in info.plist - out of scope

    // alternatively you can just provide an empty function here as the code bellow gets ignored

    LaunchedEffect(darkTheme) {
        UIApplication.sharedApplication.setStatusBarStyle(
            if (darkTheme) UIStatusBarStyleLightContent else UIStatusBarStyleDarkContent
        )
    }
}