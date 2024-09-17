package br.com.fiap.runmail.telas.email



import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import br.com.fiap.runmail.telas.preferencias.PreferencesManager


@Composable
fun RunmailTheme(preferencesManager: PreferencesManager, content: @Composable () -> Unit) {
    val isDarkTheme by preferencesManager.themePreference.collectAsState(initial = false)
    val colors = if (isDarkTheme) {
        darkColorScheme2()
    } else {
        lightColorScheme()
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography(),
        content = content
    )
}

internal object PaletteTokens {
    val Black = Color(red = 0, green = 0, blue = 0)
    val Error0 = Color(red = 0, green = 0, blue = 0)
    val Error10 = Color(red = 65, green = 14, blue = 11)
    val Error100 = Color(red = 255, green = 255, blue = 255)
    val Error20 = Color(red = 96, green = 20, blue = 16)
    val Error30 = Color(red = 140, green = 29, blue = 24)
    val Error40 = Color(red = 179, green = 38, blue = 30)
    val Error50 = Color(red = 220, green = 54, blue = 46)
    val Error60 = Color(red = 228, green = 105, blue = 98)
    val Error70 = Color(red = 236, green = 146, blue = 142)
    val Error80 = Color(red = 242, green = 184, blue = 181)
    val Error90 = Color(red = 249, green = 222, blue = 220)
    val Error95 = Color(red = 252, green = 238, blue = 238)
    val Error99 = Color(red = 255, green = 251, blue = 249)
    val Neutral0 = Color(red = 0, green = 0, blue = 0)
    val Neutral10 = Color(red = 28, green = 27, blue = 31)
    val Neutral100 = Color(red = 255, green = 255, blue = 255)
    val Neutral20 = Color(red = 49, green = 48, blue = 51)
    val Neutral30 = Color(red = 72, green = 70, blue = 73)
    val Neutral40 = Color(red = 96, green = 93, blue = 98)
    val Neutral50 = Color(red = 120, green = 117, blue = 121)
    val Neutral60 = Color(red = 147, green = 144, blue = 148)
    val Neutral70 = Color(red = 174, green = 170, blue = 174)
    val Neutral80 = Color(red = 201, green = 197, blue = 202)
    val Neutral90 = Color(red = 110, green = 62, blue = 194, alpha = 255)
    val Neutral95 = Color(red = 244, green = 239, blue = 244)
    val Neutral99 = Color(red = 255, green = 251, blue = 254)
    val NeutralVariant0 = Color(red = 0, green = 0, blue = 0)
    val NeutralVariant10 = Color(red = 29, green = 26, blue = 34)
    val NeutralVariant100 = Color(red = 255, green = 255, blue = 255)
    val NeutralVariant20 = Color(red = 50, green = 47, blue = 55)
    val NeutralVariant30 = Color(red = 73, green = 69, blue = 79)
    val NeutralVariant40 = Color(red = 96, green = 93, blue = 102)
    val NeutralVariant50 = Color(red = 121, green = 116, blue = 126)
    val NeutralVariant60 = Color(red = 147, green = 143, blue = 153)
    val NeutralVariant70 = Color(red = 174, green = 169, blue = 180)
    val NeutralVariant80 = Color(red = 202, green = 196, blue = 208)
    val NeutralVariant90 = Color(red = 231, green = 224, blue = 236)
    val NeutralVariant95 = Color(red = 245, green = 238, blue = 250)
    val NeutralVariant99 = Color(red = 255, green = 251, blue = 254)
    val Primary0 = Color(red = 0, green = 0, blue = 0)
    val Primary10 = Color(red = 33, green = 0, blue = 93)
    val Primary100 = Color(red = 255, green = 255, blue = 255)
    val Primary20 = Color(red = 56, green = 30, blue = 114)
    val Primary30 = Color(red = 79, green = 55, blue = 139)
    val Primary40 = Color(red = 103, green = 80, blue = 164)
    val Primary50 = Color(red = 127, green = 103, blue = 190)
    val Primary60 = Color(red = 154, green = 130, blue = 219)
    val Primary70 = Color(red = 182, green = 157, blue = 248)
    val Primary80 = Color(red = 208, green = 188, blue = 255)
    val Primary90 = Color(red = 234, green = 221, blue = 255)
    val Primary95 = Color(red = 246, green = 237, blue = 255)
    val Primary99 = Color(red = 255, green = 251, blue = 254)
    val Secondary0 = Color(red = 0, green = 0, blue = 0)
    val Secondary10 = Color(red = 29, green = 25, blue = 43)
    val Secondary100 = Color(red = 255, green = 255, blue = 255)
    val Secondary20 = Color(red = 51, green = 45, blue = 65)
    val Secondary30 = Color(red = 74, green = 68, blue = 88)
    val Secondary40 = Color(red = 98, green = 91, blue = 113)
    val Secondary50 = Color(red = 122, green = 114, blue = 137)
    val Secondary60 = Color(red = 149, green = 141, blue = 165)
    val Secondary70 = Color(red = 176, green = 167, blue = 192)
    val Secondary80 = Color(red = 204, green = 194, blue = 220)
    val Secondary90 = Color(red = 232, green = 222, blue = 248)
    val Secondary95 = Color(red = 246, green = 237, blue = 255)
    val Secondary99 = Color(red = 255, green = 251, blue = 254)
    val Tertiary0 = Color(red = 0, green = 0, blue = 0)
    val Tertiary10 = Color(red = 49, green = 17, blue = 29)
    val Tertiary100 = Color(red = 255, green = 255, blue = 255)
    val Tertiary20 = Color(red = 73, green = 37, blue = 50)
    val Tertiary30 = Color(red = 99, green = 59, blue = 72)
    val Tertiary40 = Color(red = 125, green = 82, blue = 96)
    val Tertiary50 = Color(red = 152, green = 105, blue = 119)
    val Tertiary60 = Color(red = 181, green = 131, blue = 146)
    val Tertiary70 = Color(red = 210, green = 157, blue = 172)
    val Tertiary80 = Color(red = 239, green = 184, blue = 200)
    val Tertiary90 = Color(red = 255, green = 216, blue = 228)
    val Tertiary95 = Color(red = 255, green = 236, blue = 241)
    val Tertiary99 = Color(red = 255, green = 251, blue = 250)
    val White = Color(red = 255, green = 255, blue = 255)
}

internal object ColorDarkTokens {
    val Background = PaletteTokens.Neutral10
    val Error = PaletteTokens.Error80
    val ErrorContainer = PaletteTokens.Error30
    val InverseOnSurface = PaletteTokens.Neutral20
    val InversePrimary = PaletteTokens.Primary40
    val InverseSurface = PaletteTokens.Neutral90
    val OnBackground = PaletteTokens.Neutral90
    val OnError = PaletteTokens.Error20
    val OnErrorContainer = PaletteTokens.Error90
    val OnPrimary = PaletteTokens.Primary20
    val OnPrimaryContainer = PaletteTokens.Primary90
    val OnSecondary = PaletteTokens.Secondary20
    val OnSecondaryContainer = PaletteTokens.Secondary90
    val OnSurface = PaletteTokens.Neutral90
    val OnSurfaceVariant = PaletteTokens.NeutralVariant80
    val OnTertiary = PaletteTokens.Tertiary20
    val OnTertiaryContainer = PaletteTokens.Tertiary90
    val Outline = PaletteTokens.NeutralVariant60
    val OutlineVariant = PaletteTokens.NeutralVariant30
    val Primary = PaletteTokens.Primary80
    val PrimaryContainer = PaletteTokens.Primary30
    val Scrim = PaletteTokens.Neutral0
    val Secondary = PaletteTokens.Secondary80
    val SecondaryContainer = PaletteTokens.Secondary30
    val Surface = PaletteTokens.Neutral10
    val SurfaceTint = Primary
    val SurfaceVariant = PaletteTokens.NeutralVariant30
    val Tertiary = PaletteTokens.Tertiary80
    val TertiaryContainer = PaletteTokens.Tertiary30
}

fun darkColorScheme2(
    primary: Color = ColorDarkTokens.Primary,
    onPrimary: Color = ColorDarkTokens.OnPrimary,
    primaryContainer: Color = ColorDarkTokens.PrimaryContainer,
    onPrimaryContainer: Color = ColorDarkTokens.OnPrimaryContainer,
    inversePrimary: Color = ColorDarkTokens.InversePrimary,
    secondary: Color = ColorDarkTokens.Secondary,
    onSecondary: Color = ColorDarkTokens.OnSecondary,
    secondaryContainer: Color = ColorDarkTokens.SecondaryContainer,
    onSecondaryContainer: Color = ColorDarkTokens.OnSecondaryContainer,
    tertiary: Color = ColorDarkTokens.Tertiary,
    onTertiary: Color = ColorDarkTokens.OnTertiary,
    tertiaryContainer: Color = ColorDarkTokens.TertiaryContainer,
    onTertiaryContainer: Color = ColorDarkTokens.OnTertiaryContainer,
    background: Color = ColorDarkTokens.Background,
    onBackground: Color = ColorDarkTokens.OnBackground,
    surface: Color = ColorDarkTokens.Surface,
    onSurface: Color = ColorDarkTokens.OnSurface,
    surfaceVariant: Color = ColorDarkTokens.SurfaceVariant,
    onSurfaceVariant: Color = ColorDarkTokens.OnSurfaceVariant,
    surfaceTint: Color = primary,
    inverseSurface: Color = ColorDarkTokens.InverseSurface,
    inverseOnSurface: Color = ColorDarkTokens.InverseOnSurface,
    error: Color = ColorDarkTokens.Error,
    onError: Color = ColorDarkTokens.OnError,
    errorContainer: Color = ColorDarkTokens.ErrorContainer,
    onErrorContainer: Color = ColorDarkTokens.OnErrorContainer,
    outline: Color = ColorDarkTokens.Outline,
    outlineVariant: Color = ColorDarkTokens.OutlineVariant,
    scrim: Color = ColorDarkTokens.Scrim,
): ColorScheme =
    ColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        inversePrimary = inversePrimary,
        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = onSecondaryContainer,
        tertiary = tertiary,
        onTertiary = onTertiary,
        tertiaryContainer = tertiaryContainer,
        onTertiaryContainer = onTertiaryContainer,
        background = background,
        onBackground = onBackground,
        surface = surface,
        onSurface = onSurface,
        surfaceVariant = surfaceVariant,
        onSurfaceVariant = onSurfaceVariant,
        surfaceTint = surfaceTint,
        inverseSurface = inverseSurface,
        inverseOnSurface = inverseOnSurface,
        error = error,
        onError = onError,
        errorContainer = errorContainer,
        onErrorContainer = onErrorContainer,
        outline = outline,
        outlineVariant = outlineVariant,
        scrim = scrim,
    )
