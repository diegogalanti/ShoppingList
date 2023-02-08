package com.gallardo.shoppinglist.presentation.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gallardo.shoppinglist.R
import com.gallardo.shoppinglist.presentation.event.ShoppingListCreateEvent


@Composable
fun ShoppingListCreateBAB(
    modifier: Modifier = Modifier,
    paperColor: PaperSheetTexture,
    onEvent: (ShoppingListCreateEvent) -> Unit,
    penColor: Color
) {
    var colorExpanded by remember { mutableStateOf(false) }
    var textureExpanded by remember { mutableStateOf(false) }
    var showDiscartChangesDialog by remember { mutableStateOf(false) }
    BottomAppBar(
        modifier = modifier,
        actions = {
            IconButton(onClick = { onEvent(ShoppingListCreateEvent.ListSaveEvent) }) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = stringResource(id = R.string.save_button_description)
                )
            }
            IconButton(onClick = {
                showDiscartChangesDialog = true
            }) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.close_button_description)
                )
            }
            IconButton(
                onClick = { colorExpanded = true },
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_draw_24),
                    contentDescription = stringResource(id = R.string.change_pen_color_button_description),
                    tint = penColor
                )
                ColorChangeDropDownMenu(onItemClick = {
                    onEvent(it)
                    colorExpanded = false
                }, expanded = colorExpanded, onDismissRequest = { colorExpanded = false })
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { textureExpanded = true },
                elevation = FloatingActionButtonDefaults.elevation(1.dp)
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
                TextureChangeDropDownMenu(onItemClick = {
                    onEvent(it)
                    textureExpanded = false
                }, expanded = textureExpanded, onDismissRequest = { textureExpanded = false })
            }
        }
    )
    if (showDiscartChangesDialog) {
        AlertDialog(
            onDismissRequest = { showDiscartChangesDialog = false },
            text = {
                Text(
                    "Do you want to discard changes and exit? "
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDiscartChangesDialog = false
                        onEvent(ShoppingListCreateEvent.ListCloseEvent)
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDiscartChangesDialog = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
}

@Composable
private fun ColorChangeDropDownMenu(
    expanded: Boolean,
    modifier: Modifier = Modifier,
    onItemClick: (ShoppingListCreateEvent.PenColorChangeEvent) -> Unit,
    onDismissRequest: () -> Unit
) {
    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        PenColor.values().forEach {
            DropdownMenuItem(
                onClick = {
                    onItemClick(ShoppingListCreateEvent.PenColorChangeEvent(it.ordinal))
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_baseline_draw_24),
                        contentDescription = stringResource(id = R.string.change_pen_color_button_description),
                        tint = it.color
                    )
                },
                text = {
                    Text(text = it.colorName)
                }
            )
        }
    }
}


@Composable
private fun TextureChangeDropDownMenu(
    expanded: Boolean,
    modifier: Modifier = Modifier,
    onItemClick: (ShoppingListCreateEvent.TextureChangeEvent) -> Unit,
    onDismissRequest: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        //futere task: calculate window height minus appbar height
        modifier = modifier
            .height(280.dp)
            .width(81.dp),
    ) {
        PaperSheetTexture.values().forEach {
            DropdownMenuItem(
                onClick = {
                    onItemClick(ShoppingListCreateEvent.TextureChangeEvent(it.ordinal))
                },
                text = {
                    Column() {
                        Box(modifier = Modifier.size(56.dp)) { //Same size from the FAB
                            AsyncImage(
                                model = it.resId,
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
