package com.gallardo.shoppinglist.domain.repository

import com.gallardo.shoppinglist.data.database.dao.ShoppingListItemDao
import com.gallardo.shoppinglist.data.database.model.asDomainModel
import com.gallardo.shoppinglist.domain.model.ShoppingListItem
import com.gallardo.shoppinglist.domain.model.asDatabaseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShoppingListItemRepImpl(private val dao: ShoppingListItemDao): ShoppingListItemRep {
    override fun getShoppingListItems(): Flow<List<ShoppingListItem>> {
        return dao.getShoppingListItems().map {
            it.map { currentListItem ->
                currentListItem.asDomainModel()
            }
        }
    }

    override suspend fun getShoppingListItem(id: Int): ShoppingListItem {
        return dao.getShoppingListItem(id).asDomainModel()
    }

    override suspend fun insertShoppingListItem(item: ShoppingListItem) {
        dao.insertShoppingListItem(item.asDatabaseEntity())
    }

    override suspend fun deleteShoppingListItem(item: ShoppingListItem) {
        dao.deleteShoppingListItem(item.asDatabaseEntity())
    }
}