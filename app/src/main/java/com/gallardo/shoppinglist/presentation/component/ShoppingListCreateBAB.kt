package com.gallardo.shoppinglist.presentation.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gallardo.shoppinglist.R
import com.gallardo.shoppinglist.presentation.event.ShoppingListCreateEvent


@Composable
fun ShoppingListCreateBAB(modifier:Modifier = Modifier, paperColor: PaperSheetColor, onEvent: (ShoppingListCreateEvent) -> Unit, penColor: Color) {
    BottomAppBar(
        modifier = modifier,
        actions = {
            IconButton(onClick = { onEvent(ShoppingListCreateEvent.ListSaveEvent) }) {
                Icon(Icons.Filled.Check, contentDescription = stringResource(id = R.string.save_button_description))
            }
            IconButton(onClick = { onEvent(ShoppingListCreateEvent.ListCloseEvent) }) {
                Icon(Icons.Filled.Close, contentDescription = stringResource(id = R.string.close_button_description))
            }
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_draw_24),
                    contentDescription = stringResource(id = R.string.change_pen_color_button_description),
                    tint = penColor
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* do something */ },
                elevation = FloatingActionButtonDefaults.elevation(1.dp)
            ) {
                Box(modifier = Modifier.size(56.dp)) { //Same size from the FAB
                    Image(
                        painter = painterResource(id = paperColor.resId),
                        contentDescription = stringResource(id = R.string.change_paper_color_button_description),
                        contentScale = ContentScale.FillBounds,
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
}