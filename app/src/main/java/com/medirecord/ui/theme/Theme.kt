package com.medirecord.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1B8A6B),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB8F0DC),
    onPrimaryContainer = Color(0xFF002114),
    secondary = Color(0xFF4C6359),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFCEE9DB),
    onSecondaryContainer = Color(0xFF082016),
    tertiary = Color(0xFF3F6375),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFC3E8FD),
    background = Color(0xFFFBFDF9),
    surface = Color(0xFFFBFDF9),
    error = Color(0xFFBA1A1A)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF9DD4C1),
    onPrimary = Color(0xFF003828),
    primaryContainer = Color(0xFF00513B),
    onPrimaryContainer = Color(0xFFB8F0DC),
    secondary = Color(0xFFB3CDC0),
    onSecondary = Color(0xFF1E352B),
    secondaryContainer = Color(0xFF354B41),
    onSecondaryContainer = Color(0xFFCEE9DB),
    tertiary = Color(0xFFA7CCDF),
    onTertiary = Color(0xFF0A3444),
    tertiaryContainer = Color(0xFF264B5C),
    background = Color(0xFF191C1A),
    surface = Color(0xFF191C1A),
    error = Color(0xFFFFB4AB)
)

@Composable
fun MediRecordTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
