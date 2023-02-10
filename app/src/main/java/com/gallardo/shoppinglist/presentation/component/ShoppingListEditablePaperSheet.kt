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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gallardo.shoppinglist.R
import com.gallardo.shoppinglist.presentation.TransparentHintTextField
import com.gallardo.shoppinglist.presentation.theme.*
import kotlin.math.roundToInt

@Composable
fun ShoppingListEditablePaperSheet(
    modifier: Modifier = Modifier,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
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
                    if(!isDarkTheme) {
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
                    TransparentHintTextField(
                        hint = stringResource(id = R.string.title_hint),
                        onValueChange = onNameChange,
                        text = titleValue,
                        textStyle = MaterialTheme.typography.headlineSmall.copy(
                            color = fontColor
                        )
                    )
                }
                Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Bottom) {
                    TransparentHintTextField(
                        modifier = Modifier.weight(1f),
                        hint = "Shopping list description...",
                        onValueChange = onDescriptionChange,
                        text = descriptionValue,
                        textStyle = MaterialTheme.typography.bodySmall.copy(
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

enum class PaperSheetStyle(val cornerRadius: Dp, val holeRadius: Dp) {
    SQUARE_NOTEBOOK(0.dp, 10.dp),
    ROUNDED_NOTEBOOK(8.dp, 10.dp),
    SQUARE_BINDER(0.dp, 20.dp),
    ROUNDED_BINDER(8.dp, 20.dp);

    companion object {
        fun randomStyle(): PaperSheetStyle = values().random()
    }
}

enum class PaperSheetTexture(val resId: Int) {
    BLUE(R.drawable.blue),
    BROWN_1(R.drawable.brown),
    BROWN_2(R.drawable.brown2),
    BROWN_3(R.drawable.brown3),
    BROWN_4(R.drawable.brown4),
    OFF_WHITE_1(R.drawable.off_white),
    OFF_WHITE_2(R.drawable.off_white2),
    OFF_WHITE_3(R.drawable.off_white3),
    OFF_WHITE_4(R.drawable.off_white4),
    WHITE_1(R.drawable.white),
    WHITE_2(R.drawable.white2),
    WHITE_DOTED(R.drawable.white_doted),
    YELLOW(R.drawable.yellow),
    YELLOW_2(R.drawable.yellow2);

    companion object {
        fun randomColor(): PaperSheetTexture = values().random()
    }
}

enum class PenColor(val color: Color, val colorName : String) {
    BLUE(pen_color_blue, "Blue"),
    RED(pen_color_red, "Red"),
    BLACK(pen_color_black, "Black"),
    GREEN(pen_color_green, "Green");

}