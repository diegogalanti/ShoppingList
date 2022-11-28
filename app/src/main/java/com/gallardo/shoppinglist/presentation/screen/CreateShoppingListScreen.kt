package com.gallardo.shoppinglist.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gallardo.shoppinglist.presentation.component.*
import com.gallardo.shoppinglist.presentation.event.ShoppingListCreateEvent
import com.gallardo.shoppinglist.presentation.view_model.ShoppingListCreateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateShoppingListScreen(modifier: Modifier = Modifier, onClose: () -> Unit) {
    val viewModel = hiltViewModel<ShoppingListCreateViewModel>()
    val state = viewModel.uiState.collectAsState().value

    if(state.shouldClose){
        LaunchedEffect(state) {
            onClose()
        }
    }

    Scaffold(
        bottomBar = {
            ShoppingListCreateBAB(
                paperColor = PaperSheetColor.values()[state.color],
                onEvent = {
                    viewModel.onEvent(it)
                }
            )
        }
    ) { padding ->
        Column(
            modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)) {
            ShoppingListPaperSheet(
                modifier = modifier.padding(16.dp),
                descriptionValue = state.description ?: "",
                titleValue = state.name,
                onDescriptionChange = { viewModel.onEvent(ShoppingListCreateEvent.DescriptionChangeEvent(it)) },
                onNameChange = { viewModel.onEvent(ShoppingListCreateEvent.NameChangeEvent(it)) },
                paperColor = PaperSheetColor.values()[state.color],
                paperStyle = PaperSheetStyle.values()[state.type?:0]
            ) {
                Column(
                    modifier
                        .fillMaxWidth()
                ) {
                    state.itemList.forEachIndexed { index, currentItem ->
                        ShoppingListPaperSheetLine(
                            description = currentItem.description,
                            quantity = currentItem.quantity?:"1",
                            onDescriptionChange = {viewModel.onEvent(ShoppingListCreateEvent.ItemDescriptionChangeEvent(index, it))},
                            onQuantityChange = {viewModel.onEvent(ShoppingListCreateEvent.ItemQuantityChangeEvent(index, it))}
                        )
                        Row(horizontalArrangement = Arrangement.Center) {
                            Divider(
                                modifier.fillMaxWidth(),
                                color = Color(0xFF4e4e4e),
                                thickness = 1.dp
                            )
                        }
                    }
                }
            }
        }
    }

}
