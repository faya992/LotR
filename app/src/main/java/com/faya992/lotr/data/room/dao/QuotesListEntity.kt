package com.faya992.lotr.data.room.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.faya992.lotr.data.room.dao.QuotesListEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class QuotesListEntity(
    @PrimaryKey     @ColumnInfo(name = "id") val id: String = "",
    @ColumnInfo(name = "dialog") val dialog: String,
    @ColumnInfo(name = "movie") val movie: String,
    @ColumnInfo(name = "character") val character: String,
    @ColumnInfo(name = "name") val name: String
) {
    companion object {
        const val TABLE_NAME = "quotes_list_entity_table"
    }
}
