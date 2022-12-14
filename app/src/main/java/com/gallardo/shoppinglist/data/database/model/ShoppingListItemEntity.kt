package com.gallardo.shoppinglist.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.gallardo.shoppinglist.domain.model.ShoppingListItem

//ForeignKey will ensure that when a list is deleted, all items are deleted with it (Cascaded)
@Entity(
    foreignKeys = [ForeignKey(
        entity = ShoppingListEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("listId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class ShoppingListItemEntity(
    val description: String,
    val quantity: String?,
    val done: Boolean,
    @ColumnInfo(index = true)
    val listId: Int,
    @PrimaryKey(autoGenerate = true) val id: Int?
)

fun ShoppingListItemEntity.asDomainModel() = ShoppingListItem(description, quantity, done, listId, id)
