package com.gallardo.shoppinglist.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gallardo.shoppinglist.presentation.component.PaperSheetColor
import com.gallardo.shoppinglist.presentation.component.PaperSheetStyle
import com.gallardo.shoppinglist.presentation.component.ShoppingListPaperSheetSummary
import com.gallardo.shoppinglist.presentation.state.ShoppingListUiState
import com.gallardo.shoppinglist.presentation.view_model.ShoppingListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(modifier: Modifier = Modifier, onFabClick: () -> Unit) {
    val viewModel = hiltViewModel<ShoppingListViewModel>()
    val state = viewModel.uiState.collectAsState().value
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onFabClick()
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Create new list")
            }
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
                } else
                    LazyColumn(
                        modifier
                            .fillMaxSize()
                            .padding(paddingValue)
                            .padding(16.dp)
                    ) {
                        items(items = state.shoppingLists) { currentList ->
                            ShoppingListPaperSheetSummary(
                                titleValue = currentList.name,
                                descriptionValue = currentList.description?:"",
                                paperColor = PaperSheetColor.values()[currentList.color],
                                paperStyle = PaperSheetStyle.values()[currentList.type?:0]
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
            }
        }
    }

}