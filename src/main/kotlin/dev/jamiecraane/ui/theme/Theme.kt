package dev.jamiecraane.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Color set
val LightTheme = lightColors(
    primary = Color.White,
    onPrimary = Color(0xFF2A2A2A),
    secondary = Color.Black,
    onSecondary = Color.White,
    error =Color(0xFFFF8689)
)

@Composable
fun FourInARowTheme(
    isDark: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    MaterialTheme(
        colors = LightTheme,
        typography = GraphPlotterTypography
    ) {
        Surface {
            Column {
                content()
            }
        }
    }
}
