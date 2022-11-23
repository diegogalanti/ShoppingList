package com.gallardo.shoppinglist.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gallardo.shoppinglist.BuildConfig
import com.gallardo.shoppinglist.data.database.dao.ShoppingListDao
import com.gallardo.shoppinglist.data.database.dao.ShoppingListItemDao
import com.gallardo.shoppinglist.data.database.model.ShoppingListEntity
import com.gallardo.shoppinglist.data.database.model.ShoppingListItemEntity

@Database(entities = [ShoppingListEntity::class, ShoppingListItemEntity::class], version = 1)
abstract class ShoppingListDatabase: RoomDatabase() {

    abstract val shoppingListDao : ShoppingListDao

    abstract val shoppingListItemDao : ShoppingListItemDao

    companion object {
        const val DATABASE_NAME = BuildConfig.DATABASE_NAME
    }

}