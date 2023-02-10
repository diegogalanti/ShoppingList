package com.gallardo.shoppinglist.domain.repository

import com.gallardo.shoppinglist.data.database.dao.ShoppingListDao
import com.gallardo.shoppinglist.data.database.dao.ShoppingListItemDao
import com.gallardo.shoppinglist.data.database.model.asDomainModel
import com.gallardo.shoppinglist.domain.model.ShoppingList
import com.gallardo.shoppinglist.domain.model.ShoppingListItem
import com.gallardo.shoppinglist.domain.model.asDatabaseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShoppingListRepImpl(private val listDao : ShoppingListDao, private val itemDao : ShoppingListItemDao) : ShoppingListRep {
    override fun getShoppingLists(): Flow<List<ShoppingList>> {
        return listDao.getShoppingLists().map {
            it.map { currentList ->
                currentList.asDomainModel()
            }
        }
    }

    override suspend fun getShoppingListItems(listId: Int): List<ShoppingListItem> {
        return itemDao.getShoppingListItems(listId).map { it.asDomainModel() }
    }

    override suspend fun insertShoppingList(list: ShoppingList) {
        listDao.upsertShoppingList(list.asDatabaseEntity())
    }

    override suspend fun insertShoppingListItems(items: List<ShoppingListItem>) {
        itemDao.upsertShoppingListItems(items.map { it.asDatabaseEntity() })
    }

    override suspend fun deleteShoppingList(list: ShoppingList) {
        listDao.deleteShoppingList(list.asDatabaseEntity())
    }
}