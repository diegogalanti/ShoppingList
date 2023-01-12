package com.gallardo.shoppinglist.presentation.state

import androidx.compose.ui.graphics.Color
import com.gallardo.shoppinglist.domain.model.ShoppingListItemSimple
import com.gallardo.shoppinglist.domain.model.UserMessage
import com.gallardo.shoppinglist.presentation.component.PaperSheetColor
import com.gallardo.shoppinglist.presentation.component.PaperSheetStyle
import com.gallardo.shoppinglist.presentation.theme.pen_color_blue

data class ShoppingListCreateUiState(
    val name: String = "",
    val description: String? = "",
    val color: Int = PaperSheetColor.randomColor().ordinal,
    val type: Int = PaperSheetStyle.randomStyle().ordinal,
    val itemList: List<ShoppingListItemSimple> = listOf(ShoppingListItemSimple("", "")),
    val userMessages: List<UserMessage> = emptyList(),
    val shouldClose: Boolean = false,
    val penColor: Color = pen_color_blue
)