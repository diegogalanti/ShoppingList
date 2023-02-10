package com.gallardo.shoppinglist.presentation.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gallardo.shoppinglist.domain.repository.ShoppingListViewRep
import com.gallardo.shoppinglist.presentation.event.ShoppingListViewEvent
import com.gallardo.shoppinglist.presentation.state.ShoppingListViewUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewViewModel @Inject constructor(
    private val repository: ShoppingListViewRep,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val listID : Int = savedStateHandle["listId"]!!

    val uiState : StateFlow<ShoppingListViewUiState> = repository.getShoppingListWithItems(listID).map { map ->
        ShoppingListViewUiState.Success(map.entries.first().key, map.entries.first().value)
    }.stateIn(
        scope = viewModelScope,
        initialValue = ShoppingListViewUiState.Loading,
        started = SharingStarted.WhileSubscribed()
    )

    fun onEvent(event: ShoppingListViewEvent) {
        when (event) {
            is ShoppingListViewEvent.ItemClickEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.updateShoppingListItem(event.shoppingListItem.copy(done = !event.shoppingListItem.done))
                }
            }
        }

    }
}