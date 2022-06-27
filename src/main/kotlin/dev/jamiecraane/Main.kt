// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import dev.jamiecraane.ui.theme.FourInARowTheme

@Composable
@Preview
fun App() {
    FourInARowTheme {
        Text(text = "Hello")
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, state = WindowState(size = DpSize(1000.dp, 800.dp))) {
        App()
    }
}
