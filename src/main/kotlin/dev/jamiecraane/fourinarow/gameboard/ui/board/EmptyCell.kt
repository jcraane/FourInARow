package dev.jamiecraane.fourinarow.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EmptyCell(
    modifier: Modifier = Modifier,
    column: Int,
    onClickListener: (column: Int) -> Unit,
) {
    Box(
        modifier = modifier
            .size(66.dp)
            .clip(RoundedCornerShape(33.dp))
            .clickable(
                remember { MutableInteractionSource() },
                rememberRipple(bounded = true),
                onClick = {
                    onClickListener(column)
                },
            )
            .background(Color(0xFFCBCBCB), RoundedCornerShape(33.dp))
    ) {
    }
}
