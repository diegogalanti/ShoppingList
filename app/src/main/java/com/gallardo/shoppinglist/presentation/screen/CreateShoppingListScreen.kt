package com.gallardo.shoppinglist.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gallardo.shoppinglist.presentation.component.*
import com.gallardo.shoppinglist.presentation.event.ShoppingListCreateEvent
import com.gallardo.shoppinglist.presentation.theme.localColorScheme
import com.gallardo.shoppinglist.presentation.view_model.ShoppingListCreateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateShoppingListScreen(modifier: Modifier = Modifier, onClose: () -> Unit) {
    val viewModel = hiltViewModel<ShoppingListCreateViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    //testar se precisa do effect
    if(uiState.shouldClose){
        onClose()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            ShoppingListCreateBAB(
                modifier = Modifier.height(85.dp),
                paperColor = PaperSheetTexture.values()[uiState.texture],
                onEvent = {
                    viewModel.onEvent(it)
                },
                penColor = PenColor.values()[uiState.penColor].color
            )
        }
    ) { padding ->
        LaunchedEffect(uiState) {
            if(uiState.userMessages.isNotEmpty()) {
                snackbarHostState.showSnackbar(uiState.userMessages.first().message)
                viewModel.userMessageShown(uiState.userMessages.first().UUID)
            }
        }
        Column(
            modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)) {
            ShoppingListPaperSheet(
                modifier = modifier.padding(16.dp),
                descriptionValue = uiState.description ?: "",
                titleValue = uiState.name,
                onDescriptionChange = { viewModel.onEvent(ShoppingListCreateEvent.DescriptionChangeEvent(it)) },
                onNameChange = { viewModel.onEvent(ShoppingListCreateEvent.NameChangeEvent(it)) },
                paperColor = PaperSheetTexture.values()[uiState.texture],
                paperStyle = PaperSheetStyle.values()[uiState.type],
                fontColor = PenColor.values()[uiState.penColor].color
            ) {
                Column(
                    modifier
                        .fillMaxWidth()
                ) {
                    uiState.itemList.forEachIndexed { index, currentItem ->
                        ShoppingListPaperSheetLine(
                            description = currentItem.description,
                            quantity = currentItem.quantity?:"",
                            penColor = PenColor.values()[uiState.penColor].color,
                            onDescriptionChange = {viewModel.onEvent(ShoppingListCreateEvent.ItemDescriptionChangeEvent(index, it))},
                            onQuantityChange = {viewModel.onEvent(ShoppingListCreateEvent.ItemQuantityChangeEvent(index, it))}
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

}
