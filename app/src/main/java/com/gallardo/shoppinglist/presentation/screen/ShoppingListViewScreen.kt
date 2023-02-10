package com.gallardo.shoppinglist.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gallardo.shoppinglist.presentation.component.*
import com.gallardo.shoppinglist.presentation.event.ShoppingListViewEvent
import com.gallardo.shoppinglist.presentation.state.ShoppingListViewUiState
import com.gallardo.shoppinglist.presentation.theme.localColorScheme
import com.gallardo.shoppinglist.presentation.view_model.ShoppingListViewViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListViewScreen(modifier: Modifier = Modifier, onClose: () -> Unit) {
    val viewModel = hiltViewModel<ShoppingListViewViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when(val state = uiState) {
        is ShoppingListViewUiState.Success -> {
            Column(
                modifier
                    .verticalScroll(rememberScrollState())
            ) {
                ShoppingListViewPaperSheet(
                    modifier = modifier.padding(16.dp),
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
                                    viewModel.onEvent(ShoppingListViewEvent.ItemClickEvent(currentItem))
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
            }
        }
        else -> {

        }
    }
}