package com.droidcon.simplejokes.core.presentation

import platform.Foundation.NSLocale
import platform.Foundation.NSUserDefaults

actual class Localization {
    actual fun updateLocale(languageTag: String) {
        val locale = NSLocale(localeIdentifier = languageTag)
        NSUserDefaults.standardUserDefaults.setObject(
            listOf(languageTag),
            forKey = "AppleLanguages"
        )
        NSUserDefaults.standardUserDefaults.synchronize()
    }
}