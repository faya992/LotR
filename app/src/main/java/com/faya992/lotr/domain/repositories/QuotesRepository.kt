package com.faya992.lotr.domain.repositories

import androidx.lifecycle.LiveData
import com.faya992.lotr.data.room.DatabaseRepository
import com.faya992.lotr.data.room.dao.QuotesListDao
import com.faya992.lotr.data.room.dao.QuotesListEntity

class QuotesRepository (private val quoteDao: QuotesListDao):DatabaseRepository {

    override val allQuotes: LiveData<List<QuotesListEntity>>
        get() = quoteDao.getAllQuotes()

    override suspend fun insert(quote: QuotesListEntity) {
        quoteDao.insert(quote)
    }

    override suspend fun delete(quote: QuotesListEntity) {
        quoteDao.delete(quote)
    }


}