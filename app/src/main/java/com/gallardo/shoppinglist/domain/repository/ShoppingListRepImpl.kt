package com.gallardo.shoppinglist.domain.repository

import com.gallardo.shoppinglist.data.database.dao.ShoppingListDao
import com.gallardo.shoppinglist.data.database.model.asDomainModel
import com.gallardo.shoppinglist.domain.model.ShoppingList
import com.gallardo.shoppinglist.domain.model.asDatabaseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShoppingListRepImpl(private val dao : ShoppingListDao) : ShoppingListRep {
    override fun getShoppingLists(): Flow<List<ShoppingList>> {
        return dao.getShoppingLists().map {
            it.map { currentList ->
                currentList.asDomainModel()
            }
        }
    }

    override suspend fun getShoppingList(id: Int): ShoppingList {
        return dao.getShoppingList(id).asDomainModel()
    }

    override suspend fun insertShoppingList(list: ShoppingList) {
        dao.upsertShoppingList(list.asDatabaseEntity())
    }

    override suspend fun deleteShoppingList(list: ShoppingList) {
        dao.deleteShoppingList(list.asDatabaseEntity())
    }
}