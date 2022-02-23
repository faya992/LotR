package com.faya992.lotr.data.room

import androidx.lifecycle.LiveData
import com.faya992.lotr.data.room.dao.QuotesListEntity

interface DatabaseRepository {

    val allQuotes: LiveData<List<QuotesListEntity>>
    suspend fun insert(quote: QuotesListEntity)
    suspend fun delete(quote: QuotesListEntity)

}