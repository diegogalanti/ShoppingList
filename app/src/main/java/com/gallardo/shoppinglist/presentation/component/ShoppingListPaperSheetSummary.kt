package com.gallardo.shoppinglist.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.gallardo.shoppinglist.R
import com.gallardo.shoppinglist.presentation.theme.dark_red
import com.gallardo.shoppinglist.presentation.theme.light_red
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListPaperSheetSummary(
    modifier: Modifier = Modifier,
    descriptionValue: String,
    titleValue: String,
    paperTexture: PaperSheetTexture,
    paperStyle: PaperSheetStyle,
    penColor: PenColor,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    onDismiss: () -> Unit
) {
    val image = ImageBitmap.imageResource(paperTexture.resId)
    val brush =
        remember(image) { ShaderBrush(ImageShader(image, TileMode.Mirror, TileMode.Mirror)) }
    val isDarkTheme = isSystemInDarkTheme()
    val coroutineScope = rememberCoroutineScope()
    val dismissState = rememberDismissState(positionalThreshold = {
        density * 70
    })
    if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
        onDismiss()
        LaunchedEffect(key1 = true) {
            coroutineScope.launch(Dispatchers.Default) { dismissState.snapTo(DismissValue.Default) }
        }


    }
    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.StartToEnd),
        background = {
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    DismissValue.Default -> MaterialTheme.colorScheme.background
                    else -> if (isSystemInDarkTheme()) dark_red else light_red
                }
            )
            val composition by rememberLottieComposition(
                if (isSystemInDarkTheme()) LottieCompositionSpec.RawRes(
                    R.raw.ic_trash_dk
                ) else LottieCompositionSpec.RawRes(R.raw.ic_trash)
            )
            val progress by animateLottieCompositionAsState(
                composition,
                isPlaying = when (dismissState.targetValue) {
                    DismissValue.Default -> false
                    else -> true
                },
                cancellationBehavior = LottieCancellationBehavior.OnIterationFinish
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = modifier.height(35.dp)
                )
            }
        }, dismissContent = {
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
                        spaceBetween =
                            (size.width - (numberOfHoles * radiusPx * 2)) / (numberOfHoles + 1)
                        for (i in 1..numberOfHoles) {
                            drawCircle(
                                radius = radiusPx,
                                center = Offset(
                                    ((radiusPx + spaceBetween) * i) + ((i - 1) * (radiusPx)),
                                    radiusPx + 8.dp.toPx()
                                ),
                                color = backgroundColor
                            )
                            //Fake shadow as compose don't offer drawShadow yet
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
                    Column(Modifier.padding(8.dp)) {
                        Spacer(modifier = Modifier.height(radius * 2 + 8.dp))
                        Row() {
                            Text(
                                text = titleValue,
                                fontSize = 24.sp,
                                color = penColor.color,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = descriptionValue,
                                fontSize = 12.sp,
                                color = penColor.color,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Row(horizontalArrangement = Arrangement.Center) {
                            Divider(
                                Modifier.fillMaxWidth(),
                                color = Color(0xFF4e4e4e),
                                thickness = 1.dp
                            )
                        }
                    }
                }
            }
        }
    )
}