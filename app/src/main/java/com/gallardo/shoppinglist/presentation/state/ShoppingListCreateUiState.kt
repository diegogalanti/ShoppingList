package com.gallardo.shoppinglist.presentation.state

import com.gallardo.shoppinglist.domain.model.ShoppingListItemSimple
import com.gallardo.shoppinglist.domain.model.UserMessage
import com.gallardo.shoppinglist.presentation.component.PaperSheetColor
import com.gallardo.shoppinglist.presentation.component.PaperSheetStyle

data class ShoppingListCreateUiState(
    val name: String = "",
    val description: String? = "",
    val color: Int = PaperSheetColor.randomColor().ordinal,
    val type: Int? = PaperSheetStyle.randomStyle().ordinal,
    val itemList: List<ShoppingListItemSimple> = listOf(ShoppingListItemSimple()),
    val userMessages: List<UserMessage> = emptyList(),
    val shouldClose: Boolean = false
)