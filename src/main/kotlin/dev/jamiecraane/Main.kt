// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import dev.jamiecraane.domain.FourInARow
import dev.jamiecraane.ui.components.Board
import dev.jamiecraane.ui.theme.FourInARowTheme
import dev.jamiecraane.util.ViewModel

@Composable
@Preview
fun App() {
    val scope = rememberCoroutineScope()
    val viewModel = ViewModel().apply {
        init(scope)
    }
    val gameState by viewModel.gameBoard.gameStatusFlow.collectAsState(null)

    FourInARowTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            Row() {
                Text(text = "Winner: ")
                gameState?.winner?.let {
                    Text(text = it.name)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Board(
                numColumns = FourInARow.NUM_COLS,
                numRows = FourInARow.NUM_ROWS,
                onClickListener = {column ->
                    println("Add new piece at column $column")
                }
            )
        }
    }
}


fun main() = application {
    Window(onCloseRequest = ::exitApplication, state = WindowState(size = DpSize(800.dp, 700.dp))) {
        App()
    }
}
