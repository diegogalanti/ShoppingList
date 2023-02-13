package com.gallardo.shoppinglist.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gallardo.shoppinglist.presentation.component.*
import com.gallardo.shoppinglist.presentation.event.ShoppingListNavigationEvent
import com.gallardo.shoppinglist.presentation.event.ShoppingListViewEvent
import com.gallardo.shoppinglist.presentation.state.ShoppingListViewUiState
import com.gallardo.shoppinglist.presentation.theme.localColorScheme
import com.gallardo.shoppinglist.presentation.view_model.ShoppingListViewViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListViewScreen(modifier: Modifier = Modifier, onNavigationEvent: (ShoppingListNavigationEvent) -> Unit) {
    val viewModel = hiltViewModel<ShoppingListViewViewModel>()
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    Scaffold(
        topBar = { TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = { onNavigationEvent(ShoppingListNavigationEvent.CloseEvent) }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Return to previous screen"
                    )
                }
            }
        ) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    onNavigationEvent(ShoppingListNavigationEvent.CreateEditListEvent)
                },
                text = { Text(text = "Edit list") } ,
                icon = { Icon(Icons.Filled.Edit, contentDescription = "Edit list") }
            )
        }
    ) { paddingValue ->
        when (state) {
            is ShoppingListViewUiState.Success -> {
                Column(
                    modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    ShoppingListViewPaperSheet(
                        modifier = modifier
                            .padding(paddingValue)
                            .padding(16.dp),
                        descriptionValue = state.shoppingList.description ?: "",
                        titleValue = state.shoppingList.name,
                        paperStyle = PaperSheetStyle.values()[state.shoppingList.type],
                        paperTexture = PaperSheetTexture.values()[state.shoppingList.paperTexture],
                        fontColor = PenColor.values()[state.shoppingList.penColor].color
                    ) {
                        Column(
                            modifier
                                .fillMaxWidth()
                        ) {
                            state.items.forEachIndexed { index, currentItem ->
                                ShoppingListPaperSheetViewLine(
                                    description = currentItem.description,
                                    quantity = currentItem.quantity ?: "",
                                    penColor = PenColor.values()[state.shoppingList.penColor].color,
                                    done = currentItem.done,
                                    modifier.clickable {
                                        viewModel.onEvent(
                                            ShoppingListViewEvent.ItemClickEvent(
                                                currentItem
                                            )
                                        )
                                    }
                                )
                                Row(horizontalArrangement = Arrangement.Center) {
                                    Divider(
                                        modifier.fillMaxWidth(),
                                        color = MaterialTheme.localColorScheme.line_color,
                                        thickness = 1.dp
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
            else -> Unit
        }
    }
}