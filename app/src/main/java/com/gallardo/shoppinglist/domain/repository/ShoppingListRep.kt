package com.gallardo.shoppinglist.domain.repository

import com.gallardo.shoppinglist.domain.model.ShoppingList
import com.gallardo.shoppinglist.domain.model.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListRep {
    fun getShoppingLists() : Flow<List<ShoppingList>>

    suspend fun getShoppingListItems(listId: Int) : List<ShoppingListItem>

    suspend fun insertShoppingList(list: ShoppingList)

    suspend fun insertShoppingListItems(items: List<ShoppingListItem>)

    suspend fun deleteShoppingList(list: ShoppingList)
}