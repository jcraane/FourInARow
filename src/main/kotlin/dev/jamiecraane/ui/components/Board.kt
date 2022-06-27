package dev.jamiecraane.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.jamiecraane.ui.theme.FourInARowTheme

@Composable
fun Board(
    modifier: Modifier = Modifier,
    numColumns: Int,
    numRows: Int,
    onClickListener: (column: Int) -> Unit,
) {
    val numPieces by derivedStateOf { numRows * numColumns }
//    todo render the board, we might need two layers for background effect
    Background(numColumns, numPieces, onClickListener)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Background(numColumns: Int, numPieces: Int, onClickListener: (column: Int) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier
            .background(Color(0xFF258EFF), RoundedCornerShape(8.dp)),
        cells = GridCells.Fixed(numColumns),
    ) {
        items(numPieces) { index ->
            // Since we support clicking on any piece, we need to remember the column the piece belongs to to know at which column a new
            // piece should be inserted.
            EmptyCell(
                modifier = Modifier.wrapContentSize().padding(8.dp),
                column = index % numColumns,
                onClickListener = onClickListener,
            )
        }
    }
}

@Composable
@Preview
private fun BoardPreview() {
    FourInARowTheme {
        Board(
            numColumns = 7,
            numRows = 6,
            onClickListener = {},
        )
    }
}
