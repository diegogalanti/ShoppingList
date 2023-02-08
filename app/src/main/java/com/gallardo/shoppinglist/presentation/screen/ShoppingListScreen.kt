package com.gallardo.shoppinglist.presentation.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.*
import com.gallardo.shoppinglist.R
import com.gallardo.shoppinglist.domain.model.ShoppingList
import com.gallardo.shoppinglist.presentation.component.PaperSheetStyle
import com.gallardo.shoppinglist.presentation.component.PaperSheetTexture
import com.gallardo.shoppinglist.presentation.component.PenColor
import com.gallardo.shoppinglist.presentation.component.ShoppingListPaperSheetSummary
import com.gallardo.shoppinglist.presentation.event.ShoppingListEvent
import com.gallardo.shoppinglist.presentation.state.ShoppingListUiState
import com.gallardo.shoppinglist.presentation.theme.dark_red
import com.gallardo.shoppinglist.presentation.theme.light_red
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
                        items(
                            items = state.shoppingLists,
                            { listItem: ShoppingList -> listItem.id!! }) { currentList ->
                            var dismissState = rememberDismissState(positionalThreshold = {
                                density * 70
                            })
                            if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
                                viewModel.onEvent(ShoppingListEvent.DeleteListEvent(currentList))
                            }
                            SwipeToDismiss(
                                state = dismissState,
                                directions = setOf(DismissDirection.StartToEnd),
                                background = {
                                    val color by animateColorAsState(
                                        targetValue = when (dismissState.targetValue) {
                                            DismissValue.Default -> MaterialTheme.colorScheme.background
                                            else -> if (isSystemInDarkTheme()) dark_red else light_red
                                        }
                                    )
                                    val composition by rememberLottieComposition(
                                        if (isSystemInDarkTheme()) LottieCompositionSpec.RawRes(
                                            R.raw.ic_trash_dk
                                        ) else LottieCompositionSpec.RawRes(R.raw.ic_trash)
                                    )
                                    val progress by animateLottieCompositionAsState(
                                        composition,
                                        isPlaying = when (dismissState.targetValue) {
                                            DismissValue.Default -> false
                                            else -> true
                                        },
                                        cancellationBehavior = LottieCancellationBehavior.OnIterationFinish
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(color)
                                            .padding(start = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        LottieAnimation(
                                            composition = composition,
                                            progress = { progress },
                                            modifier = modifier.height(35.dp)
                                        )
                                    }
                                }, dismissContent = {
                                    ShoppingListPaperSheetSummary(
                                        titleValue = currentList.name,
                                        descriptionValue = currentList.description ?: "",
                                        paperTexture = PaperSheetTexture.values()[currentList.paperTexture],
                                        paperStyle = PaperSheetStyle.values()[currentList.type],
                                        penColor = PenColor.values()[currentList.penColor],
                                    )
                                })
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
            }
        }
    }
}