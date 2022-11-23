package com.gallardo.shoppinglist.data.database

import androidx.room.Database
import com.gallardo.shoppinglist.data.database.dao.ShoppingListDao
import com.gallardo.shoppinglist.data.database.model.ShoppingListEntity
import com.gallardo.shoppinglist.data.database.model.ShoppingListItemEntity

@Database(entities = [ShoppingListEntity::class, ShoppingListItemEntity::class], version = 1)
abstract class ShoppingListDatabase {

    abstract val shoppingListDao : ShoppingListDao

    abstract val shoppingListItemDao : ShoppingListDao

    companion object {
        const val DATABASE_NAME = "shopping_list_db"
    }

}