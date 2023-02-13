package com.gallardo.shoppinglist.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.*
import com.gallardo.shoppinglist.domain.model.ShoppingList
import com.gallardo.shoppinglist.presentation.component.PaperSheetStyle
import com.gallardo.shoppinglist.presentation.component.PaperSheetTexture
import com.gallardo.shoppinglist.presentation.component.PenColor
import com.gallardo.shoppinglist.presentation.component.ShoppingListPaperSheetSummary
import com.gallardo.shoppinglist.presentation.event.ShoppingListEvent
import com.gallardo.shoppinglist.presentation.event.ShoppingListNavigationEvent
import com.gallardo.shoppinglist.presentation.state.ShoppingListUiState
import com.gallardo.shoppinglist.presentation.view_model.ShoppingListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListSummaryScreen(
    modifier: Modifier = Modifier,
    onNavigationEvent: (ShoppingListNavigationEvent) -> Unit
) {
    val viewModel = hiltViewModel<ShoppingListViewModel>()
    val state = viewModel.uiState.collectAsState().value
    val dialogState = remember {
        mutableStateOf(false)
    }
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    onNavigationEvent(ShoppingListNavigationEvent.CreateEditListEvent)
                },
                text = { Text(text = "New list") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "Create new list") }
            )
        }
    ) { paddingValue ->
        when (state) {
            is ShoppingListUiState.Loading -> Unit
            is ShoppingListUiState.Success -> {
                if (state.shoppingLists.isEmpty()) {
                    Row(
                        modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = "No list to show, use the + button to include a new List")
                    }
                } else {
                    LazyColumn(
                        modifier
                            .padding(paddingValue),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 76.dp)
                    ) {
                        items(
                            items = state.shoppingLists,
                            { listItem: ShoppingList -> listItem.id!! }) { currentList ->
                            ShoppingListPaperSheetSummary(
                                titleValue = currentList.name,
                                descriptionValue = currentList.description ?: "",
                                paperTexture = PaperSheetTexture.values()[currentList.paperTexture],
                                paperStyle = PaperSheetStyle.values()[currentList.type],
                                penColor = PenColor.values()[currentList.penColor],
                                onDismiss = {
                                    viewModel.onEvent(ShoppingListEvent.DeleteListEvent(currentList))
                                    dialogState.value = true
                                },
                                modifier = Modifier.clickable {
                                    onNavigationEvent(
                                        ShoppingListNavigationEvent.OpenListEvent(
                                            currentList.id!!
                                        )
                                    )
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        }
    }

    if (dialogState.value) {
        AlertDialog(
            onDismissRequest = { dialogState.value = false },
            text = {
                Text(
                    "Are you sure you want to delete it?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        dialogState.value = false
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dialogState.value = false
                        viewModel.onEvent(ShoppingListEvent.UndoDeleteListEvent)
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
}