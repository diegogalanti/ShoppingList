package com.gallardo.shoppinglist.di

import android.content.Context
import androidx.room.Room
import com.gallardo.shoppinglist.data.database.ShoppingListDatabase
import com.gallardo.shoppinglist.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideShoppingListRep(database: ShoppingListDatabase) : ShoppingListRep {
        return ShoppingListRepImpl(database.shoppingListDao, database.shoppingListItemDao)
    }
    @Provides
    fun provideShoppingListCreateRep(database: ShoppingListDatabase) : ShoppingListCreateRep {
        return ShoppingListCreateRepImpl(database.shoppingListDao, database.shoppingListItemDao)
    }

    @Provides
    fun provideShoppingListViewRep(database: ShoppingListDatabase) : ShoppingListViewRep {
        return ShoppingListViewRepImpl(database.shoppingListDao, database.shoppingListItemDao)
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : ShoppingListDatabase {
        return Room.databaseBuilder(
            context = context,
            name = ShoppingListDatabase.DATABASE_NAME,
            klass = ShoppingListDatabase::class.java
        ).build()
    }
}