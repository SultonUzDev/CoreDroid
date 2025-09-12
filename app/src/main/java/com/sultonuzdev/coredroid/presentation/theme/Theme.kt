package com.sultonuzdev.coredroid.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Material 3 Light Color Scheme - Modern Green Theme (Better Elevation)
val LightColorScheme = lightColorScheme(
    primary = CoreDroidColors.Primary,
    onPrimary = CoreDroidColors.OnPrimary,
    primaryContainer = CoreDroidColors.Primary.copy(alpha = 0.08f),
    onPrimaryContainer = CoreDroidColors.Primary,

    secondary = CoreDroidColors.Secondary,
    onSecondary = CoreDroidColors.OnPrimary,
    secondaryContainer = CoreDroidColors.Secondary.copy(alpha = 0.08f),
    onSecondaryContainer = CoreDroidColors.Secondary,

    tertiary = CoreDroidColors.Info,
    onTertiary = CoreDroidColors.OnPrimary,
    tertiaryContainer = CoreDroidColors.Info.copy(alpha = 0.08f),
    onTertiaryContainer = CoreDroidColors.Info,

    error = CoreDroidColors.Error,
    onError = CoreDroidColors.OnPrimary,
    errorContainer = CoreDroidColors.Error.copy(alpha = 0.08f),
    onErrorContainer = CoreDroidColors.Error,

    background = CoreDroidColors.Background,
    onBackground = CoreDroidColors.OnBackground,

    surface = CoreDroidColors.Surface,
    onSurface = CoreDroidColors.OnSurface,
    surfaceVariant = CoreDroidColors.SurfaceVariant,
    onSurfaceVariant = CoreDroidColors.OnSurfaceVariant,

    // Better elevation colors
    surfaceTint = CoreDroidColors.Primary,

    outline = CoreDroidColors.Outline,
    outlineVariant = CoreDroidColors.OutlineVariant,

    inverseSurface = CoreDroidColors.OnSurface,
    inverseOnSurface = CoreDroidColors.Surface,
    inversePrimary = CoreDroidColors.OnPrimary,
)

// Material 3 Dark Color Scheme - Modern Dark Theme
val DarkColorScheme = darkColorScheme(
    primary = CoreDroidColors.Primary,
    onPrimary = Color(0xFF000000),
    primaryContainer = CoreDroidColors.Primary.copy(alpha = 0.15f),
    onPrimaryContainer = CoreDroidColors.Primary,

    secondary = CoreDroidColors.Secondary,
    onSecondary = Color(0xFF000000),
    secondaryContainer = CoreDroidColors.Secondary.copy(alpha = 0.15f),
    onSecondaryContainer = CoreDroidColors.Secondary,

    tertiary = CoreDroidColors.Info,
    onTertiary = Color(0xFF000000),
    tertiaryContainer = CoreDroidColors.Info.copy(alpha = 0.15f),
    onTertiaryContainer = CoreDroidColors.Info,

    error = CoreDroidColors.Error,
    onError = Color(0xFF000000),
    errorContainer = CoreDroidColors.Error.copy(alpha = 0.15f),
    onErrorContainer = CoreDroidColors.Error,

    background = CoreDroidColors.DarkBackground,
    onBackground = CoreDroidColors.DarkOnBackground,

    surface = CoreDroidColors.DarkSurface,
    onSurface = CoreDroidColors.DarkOnSurface,
    surfaceVariant = CoreDroidColors.DarkSurfaceVariant,
    onSurfaceVariant = CoreDroidColors.DarkOnSurfaceVariant,

    outline = CoreDroidColors.DarkOutline,
    outlineVariant = CoreDroidColors.DarkOutlineVariant,

    surfaceTint = CoreDroidColors.Primary,
    inverseSurface = CoreDroidColors.DarkOnSurface,
    inverseOnSurface = CoreDroidColors.DarkSurface,
    inversePrimary = CoreDroidColors.Primary,
)

@Composable
fun CoreDroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set to true for Material You support
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

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CoreDroidTypography,
        content = content
    )
}

// Theme Preview Composable
@Composable
fun CoreDroidPreview(
    content: @Composable () -> Unit
) {
    CoreDroidTheme {
        content()
    }
}

// Dark Theme Preview Composable
@Composable
fun CoreDroidDarkPreview(
    content: @Composable () -> Unit
) {
    CoreDroidTheme(darkTheme = true) {
        content()
    }
}