package com.gallardo.shoppinglist.domain.repository

import com.gallardo.shoppinglist.domain.model.ShoppingList
import kotlinx.coroutines.flow.Flow

interface ShoppingListRep {
    fun getShoppingLists() : Flow<List<ShoppingList>>

    suspend fun getShoppingList(id: Int): ShoppingList

    suspend fun insertShoppingList(list: ShoppingList)

    suspend fun deleteShoppingList(list: ShoppingList)
}