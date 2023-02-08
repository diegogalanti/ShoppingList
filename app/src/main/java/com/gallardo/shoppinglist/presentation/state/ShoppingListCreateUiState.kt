package com.gallardo.shoppinglist.presentation.state

import com.gallardo.shoppinglist.domain.model.ShoppingListItemSimple
import com.gallardo.shoppinglist.domain.model.UserMessage
import com.gallardo.shoppinglist.presentation.component.PaperSheetStyle
import com.gallardo.shoppinglist.presentation.component.PaperSheetTexture
import com.gallardo.shoppinglist.presentation.component.PenColor

data class ShoppingListCreateUiState(
    val name: String = "",
    val description: String? = "",
    val texture: Int = PaperSheetTexture.randomColor().ordinal,
    val type: Int = PaperSheetStyle.randomStyle().ordinal,
    val itemList: List<ShoppingListItemSimple> = listOf(ShoppingListItemSimple("", "")),
    val userMessages: List<UserMessage> = emptyList(),
    val shouldClose: Boolean = false,
    val penColor: Int = PenColor.BLUE.ordinal
)