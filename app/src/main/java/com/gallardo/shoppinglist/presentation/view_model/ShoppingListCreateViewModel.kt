package com.gallardo.shoppinglist.presentation.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gallardo.shoppinglist.domain.model.ShoppingList
import com.gallardo.shoppinglist.domain.model.ShoppingListItemSimple
import com.gallardo.shoppinglist.domain.model.UserMessage
import com.gallardo.shoppinglist.domain.model.asShoppingList
import com.gallardo.shoppinglist.domain.repository.ShoppingListCreateRep
import com.gallardo.shoppinglist.presentation.event.ShoppingListCreateEvent
import com.gallardo.shoppinglist.presentation.state.ShoppingListCreateUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ShoppingListCreateViewModel @Inject constructor(
    private val repository: ShoppingListCreateRep,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ShoppingListCreateUiState>(
            ShoppingListCreateUiState()
        )

    val uiState = _uiState.asStateFlow()

    fun onEvent(event: ShoppingListCreateEvent) {
        when (event) {
            is ShoppingListCreateEvent.ColorChangeEvent -> {
                _uiState.update {
                    it.copy(color = event.color)
                }
            }
            is ShoppingListCreateEvent.NameChangeEvent -> {
                _uiState.update {
                    it.copy(name = event.name)
                }
            }
            is ShoppingListCreateEvent.DescriptionChangeEvent -> {
                _uiState.update {
                    it.copy(description = event.description)
                }
            }
            is ShoppingListCreateEvent.TypeChangeEvent -> {
                _uiState.update {
                    it.copy(type = event.type)
                }
            }
            is ShoppingListCreateEvent.ItemDescriptionChangeEvent -> {
                _uiState.update { state ->
                    //CHECK IF THE ITEM WAS BLANK AND IS NOT BLANK ANYMORE THEN CREATE A NEW BLANK ITEM
                    val newState =
                        if (event.description.isNotBlank() && state.itemList[event.index].description.isBlank()) {
                            val newList = state.itemList.toMutableList()
                            newList.add(ShoppingListItemSimple("",""))
                            newList[event.index] =
                                newList[event.index].copy(description = event.description)
                            state.copy(itemList = newList)
                        }
                        //CHECK IF THE ITEM WAS NOT BLANK AND IS BLANK NOW THEN REMOVE OTHER BLANK ITEMS (ALWAYS THE LAST ONE)
                        else if (event.description.isBlank() && state.itemList[event.index].description.isNotBlank()) {
                            val newList = state.itemList.toMutableList()
                            newList[event.index] =
                                newList[event.index].copy(description = event.description)

                            state.copy(itemList = newList.filterIndexed { index, currentItem ->
                                index == event.index || currentItem.description.isNotBlank()
                            })
                        }
                        // JUST ADJUST THE ITEM DESCRIPTION
                        else {
                            val newList = state.itemList.toMutableList()
                            newList[event.index] =
                                newList[event.index].copy(description = event.description)
                            state.copy(itemList = newList)
                        }
                    newState
                }
            }
            is ShoppingListCreateEvent.ItemQuantityChangeEvent -> {
                _uiState.update { state ->
                    val newList = state.itemList.toMutableList()
                    newList[event.index] = newList[event.index].copy(quantity = event.quantity)
                    state.copy(itemList = newList)
                }
            }
            is ShoppingListCreateEvent.ListSaveEvent -> {
                if (uiState.value.name.isBlank()) {
                    _uiState.update { state ->
                        val messages = state.userMessages +
                            UserMessage(
                                "List title cannot be empty",
                                UUID = UUID.randomUUID().mostSignificantBits
                            )
                        val withoutDuplicates = messages.distinctBy {
                            it.message
                        }
                        state.copy(userMessages = withoutDuplicates)
                    }
                } else {
                    viewModelScope.launch {
                        val rowId = repository.createShoppingList(
                            ShoppingList(
                                uiState.value.name,
                                uiState.value.description,
                                uiState.value.color,
                                uiState.value.type,
                                1,
                                null
                            )
                        )
                        val newListID = repository.getShoppingListID(rowId)
                        repository.upsertShoppingListItem(uiState.value.itemList.map {
                            it.asShoppingList(newListID)
                        })
                        _uiState.update {
                            it.copy(shouldClose = true)
                        }
                    }
                }
            }
            is ShoppingListCreateEvent.ListCloseEvent -> {
                _uiState.update {
                    it.copy(shouldClose = true)
                }
            }
            is ShoppingListCreateEvent.PenColorChangeEvent -> {
                _uiState.update {
                    it.copy(penColor = event.color)
                }
            }
        }
    }

    fun userMessageShown(messageUUID: Long){
        _uiState.update { state ->
            val messages = state.userMessages.filterNot { it.UUID == messageUUID }
            state.copy(userMessages = messages)
        }
    }
}