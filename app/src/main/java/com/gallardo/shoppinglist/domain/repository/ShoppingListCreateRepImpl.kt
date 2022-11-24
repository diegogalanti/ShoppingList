package com.gallardo.shoppinglist.domain.repository

import com.gallardo.shoppinglist.data.database.dao.ShoppingListItemDao
import com.gallardo.shoppinglist.domain.model.ShoppingList
import com.gallardo.shoppinglist.domain.model.ShoppingListItem
import com.gallardo.shoppinglist.domain.model.asDatabaseEntity

class ShoppingListCreateRepImpl(private val dao: ShoppingListItemDao): ShoppingListCreateRep {
    override suspend fun createShoppingList(list: ShoppingList) : Long {
        return dao.upsertShoppingList(list.asDatabaseEntity())
    }

    override suspend fun upsertShoppingListItem(items: List<ShoppingListItem>) {
        dao.upsertShoppingListItem(items.map { it.asDatabaseEntity() })
    }

    override suspend fun getShoppingListID(rowID: Long): Int {
        return dao.getShoppingListID(rowID)
    }
}