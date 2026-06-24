package com.kingzcheung.xime.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun FloatingKeyboardContainer(
    isFloatingMode: Boolean,
    scaleFactor: Float,
    cardWidthDp: Int = 0,
    offsetX: Int,
    offsetY: Int,
    dragAreaHeightDp: Int = 48,
    onDrag: (dx: Float, dy: Float) -> Unit,
    onDragEnd: () -> Unit,
    keyboardContent: @Composable () -> Unit,
) {
    if (!isFloatingMode) {
        keyboardContent()
        return
    }

    val density = LocalDensity.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .width(if (cardWidthDp > 0) cardWidthDp.dp else androidx.compose.ui.unit.Dp.Unspecified)
                .fillMaxWidth(if (cardWidthDp > 0) 1f else scaleFactor)
                .offset(x = offsetX.dp, y = (-offsetY).dp)
                .shadow(12.dp, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
        ) {
            keyboardContent()

            DragSurface(
                modifier = Modifier.align(Alignment.TopCenter),
                dragAreaHeightDp = dragAreaHeightDp,
                onDrag = { dx, dy ->
                    onDrag(dx, -dy)
                },
                onDragEnd = onDragEnd,
            )
        }
    }
}

@Composable
private fun DragSurface(
    modifier: Modifier = Modifier,
    dragAreaHeightDp: Int,
    onDrag: (dxDp: Float, dyDp: Float) -> Unit,
    onDragEnd: () -> Unit,
) {
    val density = LocalDensity.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(dragAreaHeightDp.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dragAreaHeightDp.dp)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            val dxDp = with(density) { dragAmount.x.toDp().value }
                            val dyDp = with(density) { dragAmount.y.toDp().value }
                            onDrag(dxDp, dyDp)
                        },
                        onDragEnd = onDragEnd
                    )
                }
        )

        Box(
            modifier = Modifier
                .width(36.dp)
                .height(5.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(Color.White.copy(alpha = 0.7f))
                .align(Alignment.TopCenter)
                .offset(y = 4.dp)
        )
    }
}
