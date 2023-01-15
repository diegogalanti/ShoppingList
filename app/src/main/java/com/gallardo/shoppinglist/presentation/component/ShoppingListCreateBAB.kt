package com.gallardo.shoppinglist.presentation.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.gallardo.shoppinglist.R
import com.gallardo.shoppinglist.presentation.event.ShoppingListCreateEvent
import com.gallardo.shoppinglist.presentation.theme.*


@Composable
fun ShoppingListCreateBAB(modifier:Modifier = Modifier, paperColor: PaperSheetColor, onEvent: (ShoppingListCreateEvent) -> Unit, penColor: Color) {
    var colorExpanded by remember { mutableStateOf(false) }
    var textureExpanded by remember { mutableStateOf(false) }
    var fabPosition by remember { mutableStateOf(Offset.Zero) }
    var fabSize by remember { mutableStateOf(IntSize.Zero) }
    var penColorPosition by remember { mutableStateOf(Offset.Zero) }
    var closeDialog by remember { mutableStateOf(false) }
    BottomAppBar(
        modifier = modifier,
        actions = {
            IconButton(onClick = { onEvent(ShoppingListCreateEvent.ListSaveEvent) }) {
                Icon(Icons.Filled.Check, contentDescription = stringResource(id = R.string.save_button_description))
            }
            IconButton(onClick = {
                closeDialog = true
            }) {
                Icon(Icons.Filled.Close, contentDescription = stringResource(id = R.string.close_button_description))
            }
            IconButton(
                onClick = { colorExpanded = true },
                modifier = Modifier.onGloballyPositioned {
                    penColorPosition = it.positionInRoot()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_draw_24),
                    contentDescription = stringResource(id = R.string.change_pen_color_button_description),
                    tint = penColor
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { textureExpanded = true },
                elevation = FloatingActionButtonDefaults.elevation(1.dp),
                modifier = Modifier.onGloballyPositioned {
                    fabPosition = it.positionInRoot()
                    fabSize = it.size
                }
            ) {
                Box(modifier = Modifier.size(56.dp)) { //Same size from the FAB
                    Image(
                        painter = painterResource(id = paperColor.resId),
                        contentDescription = stringResource(id = R.string.change_paper_color_button_description),
                        contentScale = ContentScale.None,
                        modifier = Modifier.matchParentSize()
                    )
                }
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = stringResource(id = R.string.change_paper_color_button_description),
                    tint = Color.Black
                )
            }
        }
    )
    if (closeDialog) {
        AlertDialog(
            onDismissRequest = { closeDialog = false },
            text = {
                Text(
                    "Do you want to discard changes and exit? "
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        closeDialog = false
                        onEvent(ShoppingListCreateEvent.ListCloseEvent)
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        closeDialog = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
    DropdownMenu(
        expanded = colorExpanded,
        onDismissRequest = { colorExpanded = false },
        offset = DpOffset(with(LocalDensity.current){ penColorPosition.x.toDp()} + 35.dp, 0.dp)
    ) {
        DropdownMenuItem(
            onClick = {
                onEvent(ShoppingListCreateEvent.PenColorChangeEvent(pen_color_blue))
                colorExpanded = false
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_draw_24),
                    contentDescription = stringResource(id = R.string.change_pen_color_button_description),
                    tint = MaterialTheme.localColorScheme.pen_color_blue
                )
            },
            text = {
                Text(text = "Blue")
            }
        )
        DropdownMenuItem(
            onClick = {
                onEvent(ShoppingListCreateEvent.PenColorChangeEvent(pen_color_red))
                colorExpanded = false
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_draw_24),
                    contentDescription = stringResource(id = R.string.change_pen_color_button_description),
                    tint = MaterialTheme.localColorScheme.pen_color_red
                )
            },
            text = {
                Text(text = "Red")
            }
        )
        DropdownMenuItem(
            onClick = {
                onEvent(ShoppingListCreateEvent.PenColorChangeEvent(pen_color_black))
                colorExpanded = false
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_draw_24),
                    contentDescription = stringResource(id = R.string.change_pen_color_button_description),
                    tint = MaterialTheme.localColorScheme.pen_color_black
                )
            },
            text = {
                Text(text = "Black")
            }
        )
        DropdownMenuItem(
            onClick = {
                onEvent(ShoppingListCreateEvent.PenColorChangeEvent(pen_color_green))
                colorExpanded = false
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_draw_24),
                    contentDescription = stringResource(id = R.string.change_pen_color_button_description),
                    tint = MaterialTheme.localColorScheme.pen_color_green
                )
            },
            text = {
                Text(text = "Green")
            }
        )
    }
    DropdownMenu(
        expanded = textureExpanded,
        onDismissRequest = { textureExpanded = false },
        //calculate window height minus appbar height
        modifier = Modifier
            .height(280.dp)
            .width(81.dp),
        offset = DpOffset(with(LocalDensity.current){ fabPosition.x.toDp() - fabSize.width.toDp()} - 25.dp, 0.dp)
    ) {
        PaperSheetColor.values().forEach {
            DropdownMenuItem(
                onClick = {
                    onEvent(ShoppingListCreateEvent.ColorChangeEvent(it.ordinal))
                    textureExpanded = false
                },
                text = {
                    Column() {
                        Box(modifier = Modifier.size(56.dp)) { //Same size from the FAB
                            Image(
                                //Using the full image as resource it slow, we should create a smaller resource just for the icons
                                painter = painterResource(id = it.resId),
                                contentDescription = "Change paper color",
                                contentScale = ContentScale.None,
                                modifier = Modifier.matchParentSize()
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                    }
                }
            )
        }

    }
}