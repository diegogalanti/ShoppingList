package com.gallardo.shoppinglist.domain.repository

import com.gallardo.shoppinglist.data.database.dao.ShoppingListDao
import com.gallardo.shoppinglist.data.database.dao.ShoppingListItemDao
import com.gallardo.shoppinglist.data.database.model.asDomainModel
import com.gallardo.shoppinglist.domain.model.ShoppingList
import com.gallardo.shoppinglist.domain.model.ShoppingListItem
import com.gallardo.shoppinglist.domain.model.asDatabaseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShoppingListViewRepImpl(private val listDao: ShoppingListDao, private val itemDao: ShoppingListItemDao) : ShoppingListViewRep {
    override suspend fun updateShoppingListItem(item: ShoppingListItem) {
        itemDao.upsertShoppingListItem(item.asDatabaseEntity())
    }

    override fun getShoppingListWithItems(listID: Int): Flow<Map<ShoppingList, List<ShoppingListItem>>> {
        return listDao.getShoppingListWithItems(listID).map {
            it.mapValues { currentEntry ->
                currentEntry.value.map { currentValue -> currentValue.asDomainModel() }
            }.mapKeys { currentEntry ->
                currentEntry.key.asDomainModel()
            }
        }
    }
}