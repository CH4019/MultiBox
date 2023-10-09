package com.ch4019.multibox.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DragHandle(
    modifier: Modifier = Modifier,
    width: Dp = 32.0.dp,
    height: Dp = 4.dp,
    dragHandleVerticalPadding : Dp = 16.dp,
    shape: Shape = MaterialTheme.shapes.extraLarge,
    color: Color = Color.Gray.copy(0.4f)
) {
    Surface(
        modifier = modifier
            .padding(vertical = dragHandleVerticalPadding),
        color = color,
        shape = shape
    ) {
        Box(
            Modifier
                .size(
                    width = width,
                    height = height
                )
        )
    }
}