package com.gallardo.shoppinglist.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val md_theme_light_primary = Color(0xFF6D5E00)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFFCE365)
val md_theme_light_onPrimaryContainer = Color(0xFF211B00)
val md_theme_light_secondary = Color(0xFF665E40)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFEDE2BC)
val md_theme_light_onSecondaryContainer = Color(0xFF201B04)
val md_theme_light_tertiary = Color(0xFF42664F)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFC4ECCF)
val md_theme_light_onTertiaryContainer = Color(0xFF002110)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFFFBFF)
val md_theme_light_onBackground = Color(0xFF1D1B16)
val md_theme_light_surface = Color(0xFFFFFBFF)
val md_theme_light_onSurface = Color(0xFF1D1B16)
val md_theme_light_surfaceVariant = Color(0xFFE9E2D0)
val md_theme_light_onSurfaceVariant = Color(0xFF4A4739)
val md_theme_light_outline = Color(0xFF7C7768)
val md_theme_light_inverseOnSurface = Color(0xFFF6F0E7)
val md_theme_light_inverseSurface = Color(0xFF32302A)
val md_theme_light_inversePrimary = Color(0xFFDEC64C)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF6D5E00)
val md_theme_light_outlineVariant = Color(0xFFCDC6B4)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFFDEC64C)
val md_theme_dark_onPrimary = Color(0xFF393000)
val md_theme_dark_primaryContainer = Color(0xFF524600)
val md_theme_dark_onPrimaryContainer = Color(0xFFFCE365)
val md_theme_dark_secondary = Color(0xFFD0C6A2)
val md_theme_dark_onSecondary = Color(0xFF363016)
val md_theme_dark_secondaryContainer = Color(0xFF4D472B)
val md_theme_dark_onSecondaryContainer = Color(0xFFEDE2BC)
val md_theme_dark_tertiary = Color(0xFFA9D0B4)
val md_theme_dark_onTertiary = Color(0xFF133723)
val md_theme_dark_tertiaryContainer = Color(0xFF2B4E39)
val md_theme_dark_onTertiaryContainer = Color(0xFFC4ECCF)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF1D1B16)
val md_theme_dark_onBackground = Color(0xFFE7E2D9)
val md_theme_dark_surface = Color(0xFF1D1B16)
val md_theme_dark_onSurface = Color(0xFFE7E2D9)
val md_theme_dark_surfaceVariant = Color(0xFF4A4739)
val md_theme_dark_onSurfaceVariant = Color(0xFFCDC6B4)
val md_theme_dark_outline = Color(0xFF969080)
val md_theme_dark_inverseOnSurface = Color(0xFF1D1B16)
val md_theme_dark_inverseSurface = Color(0xFFE7E2D9)
val md_theme_dark_inversePrimary = Color(0xFF6D5E00)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFFDEC64C)
val md_theme_dark_outlineVariant = Color(0xFF4A4739)
val md_theme_dark_scrim = Color(0xFF000000)

val pen_color_blue: Color = Color(0xFF373acb)
val pen_color_red: Color = Color(0xFFda2e29)
val pen_color_green: Color = Color(0xFF5ca35d)
val pen_color_black: Color = Color(0xFF040404)
val line_color: Color = Color(0xFF4e4e4e)
val light_red: Color = Color(0xFFFFE6EE)
val dark_red: Color = Color(0xFF800000)

data class PaperSheetColorScheme(
    val pen_color_blue: Color,
    val pen_color_red: Color,
    val pen_color_green: Color,
    val pen_color_black: Color,
    val line_color: Color
)

val LocalColorScheme = compositionLocalOf { PaperSheetColorScheme(
    pen_color_blue = pen_color_blue,
    pen_color_red = pen_color_red,
    pen_color_green = pen_color_green,
    pen_color_black = pen_color_black,
    line_color = line_color
) }

val MaterialTheme.localColorScheme: PaperSheetColorScheme
    @Composable
    @ReadOnlyComposable
    get() = LocalColorScheme.current
