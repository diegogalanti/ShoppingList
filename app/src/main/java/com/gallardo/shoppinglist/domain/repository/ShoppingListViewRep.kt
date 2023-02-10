package com.gallardo.shoppinglist.domain.repository

import com.gallardo.shoppinglist.domain.model.ShoppingList
import com.gallardo.shoppinglist.domain.model.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListViewRep {
    suspend fun updateShoppingListItem(item: ShoppingListItem)

    fun getShoppingListWithItems(listID: Int) : Flow<Map<ShoppingList, List<ShoppingListItem>>>
}