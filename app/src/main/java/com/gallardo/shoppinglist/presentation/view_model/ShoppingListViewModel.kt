package com.gallardo.shoppinglist.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gallardo.shoppinglist.domain.model.ShoppingList
import com.gallardo.shoppinglist.domain.repository.ShoppingListRep
import com.gallardo.shoppinglist.presentation.event.ShoppingListEvent
import com.gallardo.shoppinglist.presentation.state.ShoppingListUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ShoppingListViewModel(private val repository: ShoppingListRep) : ViewModel() {

    val uiState : StateFlow<ShoppingListUiState> = repository.getShoppingLists().map {
        ShoppingListUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = ShoppingListUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )

    private var deletedList: ShoppingList? = null

    fun onEvent(event: ShoppingListEvent) {
        when (event) {
            is ShoppingListEvent.DeleteListEvent -> {
                viewModelScope.launch {
                    repository.deleteShoppingList(event.list)
                    deletedList = event.list
                }
            }
            is ShoppingListEvent.UndoDeleteListEvent -> {
                deletedList?.let {
                    viewModelScope.launch {
                        repository.insertShoppingList(it)
                    }
                }
            }
        }
    }
}