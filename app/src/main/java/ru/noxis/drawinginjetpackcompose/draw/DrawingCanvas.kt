package ru.noxis.drawinginjetpackcompose.draw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.util.fastForEach
import ru.noxis.drawinginjetpackcompose.draw.components.drawPath

@Composable
fun DrawingCanvas(
    paths: List<PathData>,
    currentPath: PathData?,
    onAction: (DrawingAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier
        .clipToBounds()
        .background(Color.White)
        .pointerInput(true) {
            detectDragGestures(
                onDragStart = { onAction(DrawingAction.OnNewPathStart) },
                onDragEnd = { onAction(DrawingAction.OnPathEnd) },
                onDragCancel = { onAction(DrawingAction.OnPathEnd) }
            ) { change, _ ->
                onAction(DrawingAction.OnDraw(change.position))
            }
        }
    ) {
        paths.fastForEach { pathData ->
            drawPath(
                path = pathData.path,
                color = pathData.color,
            )
        }
        currentPath?.let {
            drawPath(
                path = it.path,
                color = it.color
            )
        }
    }
}