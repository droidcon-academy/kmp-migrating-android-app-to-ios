package com.droidcon.cmp.core.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import platform.UIKit.UIApplication
import platform.UIKit.UIStatusBarStyleDarkContent
import platform.UIKit.UIStatusBarStyleLightContent
import platform.UIKit.setStatusBarStyle

@Composable
actual fun SetSystemBarAppearance(darkTheme: Boolean) {
    LaunchedEffect(darkTheme) {
        UIApplication.sharedApplication.setStatusBarStyle(
            UIStatusBarStyleLightContent
            //if (darkTheme) UIStatusBarStyleLightContent else UIStatusBarStyleDarkContent
        )
    }
}