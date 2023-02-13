package com.gallardo.shoppinglist.presentation.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gallardo.shoppinglist.domain.model.*
import com.gallardo.shoppinglist.domain.repository.ShoppingListCreateEditRep
import com.gallardo.shoppinglist.presentation.event.ShoppingListCreateEvent
import com.gallardo.shoppinglist.presentation.state.ShoppingListCreateUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ShoppingListCreateEditViewModel @Inject constructor(
    private val repository: ShoppingListCreateEditRep,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private lateinit var initialUiState : ShoppingListCreateUiState

    private val _uiState =
        MutableStateFlow<ShoppingListCreateUiState>(
            ShoppingListCreateUiState()
        )

    val uiState = _uiState.asStateFlow()

    init {
        savedStateHandle.get<Int>("listId")?.let { listId ->
            if (listId != -1) {
                viewModelScope.launch {
                    repository.getShoppingList(listId).also { list ->
                        _uiState.update { state ->
                            state.copy(
                                name = list.name,
                                description = list.description,
                                texture = list.paperTexture,
                                type = list.type,
                                penColor = list.penColor,
                                id = list.id,
                                screenTitle = "Edit list"
                            )
                        }
                    }
                    repository.getShoppingListItems(listId).also { items ->
                        _uiState.update { state ->
                            state.copy(itemList = items)
                        }
                    }
                    initialUiState= _uiState.value
                }
            } else {
                initialUiState= _uiState.value
            }
        }
    }

    fun onEvent(event: ShoppingListCreateEvent) {
        when (event) {
            is ShoppingListCreateEvent.TextureChangeEvent -> {
                _uiState.update {
                    it.copy(texture = event.texture)
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
                            newList.add(ShoppingListItem("", "", false, 0, null))
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
                        val rowId = repository.upsertShoppingList(
                            ShoppingList(
                                uiState.value.name,
                                uiState.value.description,
                                uiState.value.texture,
                                uiState.value.type,
                                System.currentTimeMillis(),
                                uiState.value.penColor,
                                uiState.value.id
                            )
                        )
                        val newListID = if (rowId == -1L) uiState.value.id?:0 else repository.getShoppingListID(rowId)
                        repository.upsertShoppingListItem(uiState.value.itemList.map {
                            it.copy(listId = newListID)
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

    fun userMessageShown(messageUUID: Long) {
        _uiState.update { state ->
            val messages = state.userMessages.filterNot { it.UUID == messageUUID }
            state.copy(userMessages = messages)
        }
    }

    fun shouldShowDiscardChanges(): Boolean {
        return _uiState.value != initialUiState
    }
}