package com.gallardo.shoppinglist.presentation.state

import com.gallardo.shoppinglist.domain.model.ShoppingListItemSimple
import com.gallardo.shoppinglist.domain.model.UserMessage

data class ShoppingListCreateUiState(
    val name: String = "",
    val description: String? = "",
    val color: Int = 1,
    val type: Int? = 1,
    val itemList: List<ShoppingListItemSimple> = listOf(ShoppingListItemSimple()),
    val userMessages: List<UserMessage> = emptyList(),
    val listSaved: Boolean = false
)