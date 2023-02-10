package com.gallardo.shoppinglist.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gallardo.shoppinglist.presentation.theme.*
import kotlin.math.roundToInt

@Composable
fun ShoppingListViewPaperSheet(
    modifier: Modifier = Modifier,
    descriptionValue: String,
    titleValue: String,
    paperTexture: PaperSheetTexture,
    paperStyle: PaperSheetStyle,
    fontColor: Color,
    content: @Composable () -> Unit
) {
    val image = ImageBitmap.imageResource(paperTexture.resId)
    val brush =
        remember(image) { ShaderBrush(ImageShader(image, TileMode.Mirror, TileMode.Mirror)) }
    //get the background color to paint the holes so they look transparent
    val backgroundColor = MaterialTheme.colorScheme.background
    val isDarkTheme = isSystemInDarkTheme()
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(paperStyle.cornerRadius),
        shadowElevation = 1.dp
    ) {
        Box(Modifier.background(brush)) {
            val radius = paperStyle.holeRadius
            Canvas(
                modifier = Modifier
                    .matchParentSize()
            ) {
                val radiusPx = radius.toPx()
                var spaceBetween = 8.dp.toPx()
                val numberOfHoles = when (paperStyle) {
                    PaperSheetStyle.ROUNDED_NOTEBOOK -> ((size.width - spaceBetween) / ((radiusPx * 2) + spaceBetween)).roundToInt()
                    PaperSheetStyle.SQUARE_NOTEBOOK -> ((size.width - spaceBetween) / ((radiusPx * 2) + spaceBetween)).roundToInt()
                    PaperSheetStyle.ROUNDED_BINDER -> 4
                    PaperSheetStyle.SQUARE_BINDER -> 4
                }
                spaceBetween = (size.width - (numberOfHoles * radiusPx * 2)) / (numberOfHoles + 1)
                for (i in 1..numberOfHoles) {
                    drawCircle(
                        radius = radiusPx,
                        center = Offset(
                            ((radiusPx + spaceBetween) * i) + ((i - 1) * (radiusPx)),
                            radiusPx + 8.dp.toPx()
                        ),
                        color = backgroundColor
                    )
                    //Fake shadow as compose don't offer drawShadow yet, not necessary with dark background
                    if (!isDarkTheme) {
                        drawCircle(
                            radius = radiusPx - 0.5f,
                            center = Offset(
                                ((radiusPx + spaceBetween) * i) + ((i - 1) * (radiusPx)),
                                radiusPx + 8.dp.toPx()
                            ),
                            color = Color(0xFFE0E0E0),
                            style = Stroke(1f)
                        )
                        drawCircle(
                            radius = radiusPx - 1f,
                            center = Offset(
                                ((radiusPx + spaceBetween) * i) + ((i - 1) * (radiusPx)),
                                radiusPx + 8.dp.toPx()
                            ),
                            color = Color(0xFFEFEFEF),
                            style = Stroke(1f)
                        )
                    }
                }
            }
            Column(Modifier.padding(MaterialTheme.spacingScheme.small)) {
                Spacer(modifier = Modifier.height(radius * 2 + 8.dp))
                Row() {
                    Text(
                        text = titleValue,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = fontColor
                        )
                    )
                }
                Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Bottom) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = descriptionValue,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = fontColor
                        )
                    )
                    Text(
                        "Qty",
                        color = fontColor,
                        fontSize = 12.sp,
                        modifier = Modifier.width(70.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Row(horizontalArrangement = Arrangement.Center) {
                    Divider(
                        Modifier.fillMaxWidth(),
                        color = Color(0xFF4e4e4e),
                        thickness = 1.dp
                    )
                }
                Layout(
                    content = content
                ) { measurables, constraints ->
                    // Don't constrain child views further, measure them with given constraints
                    // List of measured children
                    val placeables = measurables.map { measurable ->
                        // Measure each children
                        measurable.measure(constraints)
                    }
                    val height = placeables.map {
                        it.height
                    }.sum()
                    val width = placeables.map {
                        it.width
                    }.sum()
                    // Set the size of the layout as big as it can
                    layout(width, height) {
                        // Track the y co-ord we have placed children up to
                        var yPosition = 0
                        // Place children in the parent layout
                        placeables.forEach { placeable ->
                            // Position item on the screen
                            placeable.placeRelative(x = 0, y = yPosition)
                            // Record the y co-ord placed up to
                            yPosition += placeable.height
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}