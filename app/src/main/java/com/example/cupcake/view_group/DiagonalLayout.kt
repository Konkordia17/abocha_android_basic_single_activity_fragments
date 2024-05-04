package com.example.cupcake.view_group

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DiagonalLayout(
    texts: ImmutableList<String>,
    modifier: Modifier = Modifier
) {
    Layout(
        content = {
            texts.forEach { text ->
                Text(
                    text = text,
                    style = TextStyle(color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
            }
        },
        modifier = modifier
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            var xOffset = 0
            var yOffset = 0

            placeables.forEach { placeable ->
                placeable.placeRelative(x = xOffset, y = yOffset)
                xOffset += placeable.width
                yOffset += placeable.height
            }
        }
    }
}

@Preview
@Composable
fun PreviewDiagonalLayout() {
val inputList = persistentListOf("Sharik", "Bobik", "Gena", "Buka", "Buba")
    DiagonalLayout(texts = inputList)
}

