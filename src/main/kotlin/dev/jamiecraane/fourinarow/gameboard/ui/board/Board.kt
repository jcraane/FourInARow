package dev.jamiecraane.fourinarow.common.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.jamiecraane.fourinarow.gameboard.viewcontroller.PieceViewModel
import dev.jamiecraane.fourinarow.common.ui.theme.FourInARowTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Board(
    modifier: Modifier = Modifier,
    numColumns: Int,
    numRows: Int,
    playedPieces: List<PieceViewModel>,
    onClickListener: (column: Int) -> Unit,
) {
    BoxWithConstraints {
        Row(
            modifier = Modifier
                .background(Color(0xFF258EFF), RoundedCornerShape(8.dp))
        ) {
            repeat(numColumns) { columnIndex ->
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
//                    todo move logic to viewmodel
                    val piecesForColumn = playedPieces.filter { it.column == columnIndex }
                    val emptyPieces = numRows - piecesForColumn.size

                    repeat(emptyPieces) {
                        EmptyCell(
                            modifier = Modifier.wrapContentSize().padding(8.dp),
                            column = columnIndex,
                            onClickListener = onClickListener,
                        )
                    }

                    piecesForColumn.reversed().forEach {
                        Piece(
                            modifier = Modifier.wrapContentSize().padding(8.dp),
                            color = it.color,
                            column = columnIndex,
                            onClickListener = onClickListener,
                        )
                    }
                }
            }
        }
    }
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
            // Since we support clicking on any piece, we need to remember the column the piece belongs to, so we know at which column a
            // new piece should be inserted.
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
            playedPieces = emptyList(),
        )
    }
}
