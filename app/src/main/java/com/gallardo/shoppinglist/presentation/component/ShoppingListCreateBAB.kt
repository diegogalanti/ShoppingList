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
import androidx.compose.ui.unit.dp
import com.gallardo.shoppinglist.presentation.event.ShoppingListCreateEvent


@Composable
fun ShoppingListCreateBAB(paperColor: PaperSheetColor, onEvent: (ShoppingListCreateEvent) -> Unit) {
    BottomAppBar(
        modifier = Modifier.height(85.dp),
        actions = {
            IconButton(onClick = { onEvent(ShoppingListCreateEvent.ListSaveEvent) }) {
                Icon(Icons.Filled.Check, contentDescription = "Save list button")
            }
            IconButton(onClick = { onEvent(ShoppingListCreateEvent.ListCloseEvent) }) {
                Icon(Icons.Filled.Close, contentDescription = "Close list button")
            }
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    painter = painterResource(com.gallardo.shoppinglist.R.drawable.ic_baseline_draw_24),
                    contentDescription = "Change pen color button",
                    tint = Color(0xFF373acb)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* do something */ },
                elevation = FloatingActionButtonDefaults.elevation(1.dp)
            ) {
                Box(modifier = Modifier.size(56.dp)) {
                    Image(
                        painter = painterResource(id = paperColor.resId),
                        contentDescription = "Floating Action Button",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.matchParentSize()
                    )
                }
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Localized description",
                    tint = Color.Black
                )
            }
        }
    )
}