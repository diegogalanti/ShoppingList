package com.gallardo.shoppinglist.domain.repository

import com.gallardo.shoppinglist.data.database.dao.ShoppingListDao
import com.gallardo.shoppinglist.data.database.dao.ShoppingListItemDao
import com.gallardo.shoppinglist.data.database.model.asDomainModel
import com.gallardo.shoppinglist.domain.model.ShoppingList
import com.gallardo.shoppinglist.domain.model.ShoppingListItem
import com.gallardo.shoppinglist.domain.model.asDatabaseEntity

class ShoppingListCreateEditRepImpl(private val listDao: ShoppingListDao, private val itemDao: ShoppingListItemDao): ShoppingListCreateEditRep {
    override suspend fun getShoppingList(listId: Int): ShoppingList {
        return listDao.getShoppingList(listId).asDomainModel()
    }

    override suspend fun getShoppingListItems(listId: Int): List<ShoppingListItem> {
        return itemDao.getShoppingListItems(listId).map {
            it.asDomainModel()
        }
    }

    override suspend fun upsertShoppingList(list: ShoppingList) : Long {
        return listDao.upsertShoppingList(list.asDatabaseEntity())
    }

    override suspend fun upsertShoppingListItem(items: List<ShoppingListItem>) {
        itemDao.upsertShoppingListItems(items.map { it.asDatabaseEntity() })
    }

    override suspend fun getShoppingListID(rowID: Long): Int {
        return listDao.getShoppingListID(rowID)
    }
}