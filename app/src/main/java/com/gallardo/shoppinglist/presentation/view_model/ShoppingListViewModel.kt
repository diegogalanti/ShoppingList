package com.gallardo.shoppinglist.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gallardo.shoppinglist.domain.model.ShoppingList
import com.gallardo.shoppinglist.domain.model.ShoppingListItem
import com.gallardo.shoppinglist.domain.repository.ShoppingListRep
import com.gallardo.shoppinglist.presentation.event.ShoppingListEvent
import com.gallardo.shoppinglist.presentation.state.ShoppingListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(private val repository: ShoppingListRep) :
    ViewModel() {

    val uiState: StateFlow<ShoppingListUiState> = repository.getShoppingLists().map { list ->
        ShoppingListUiState.Success(list.sortedByDescending { item ->
            item.timestamp
        })
    }.stateIn(
        scope = viewModelScope,
        initialValue = ShoppingListUiState.Loading,
        started = SharingStarted.WhileSubscribed()
    )
    private var deletedList: ShoppingList? = null
    private var deletedItems: List<ShoppingListItem>? = null
    fun onEvent(event: ShoppingListEvent) {
        when (event) {
            is ShoppingListEvent.DeleteListEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deletedItems = event.list.id?.let { repository.getShoppingListItems(it) }
                    deletedList = event.list
                    repository.deleteShoppingList(event.list)
                }
            }
            is ShoppingListEvent.UndoDeleteListEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deletedList?.let {
                        repository.insertShoppingList(it)
                    }
                    deletedItems?.let {
                        repository.insertShoppingListItems(it)
                    }
                }
            }
        }
    }
}