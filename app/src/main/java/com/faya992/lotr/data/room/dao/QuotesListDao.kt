package com.faya992.lotr.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuotesListDao {

    @Query("SELECT * FROM ${QuotesListEntity.TABLE_NAME}")
    fun getAllQuotes(): LiveData<List<QuotesListEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(quote: QuotesListEntity)

    @Delete
    suspend fun delete(quote: QuotesListEntity)
}