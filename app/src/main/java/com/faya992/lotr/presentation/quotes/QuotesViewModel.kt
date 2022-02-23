package com.faya992.lotr.presentation.quotes

import android.app.Application
import androidx.lifecycle.*
import com.faya992.lotr.data.models.QuoteRemote
import com.faya992.lotr.data.room.QuotesDatabase
import com.faya992.lotr.data.room.dao.QuotesListEntity
import com.faya992.lotr.data.services.QuotesService
import com.faya992.lotr.domain.repositories.QuotesRepository
import com.faya992.lotr.domain.helpers.REPOSITORY
import com.faya992.lotr.domain.helpers.TYPE_ROOM
import kotlinx.coroutines.*

class QuotesViewModel(application: Application, private val quotesService: QuotesService) : AndroidViewModel(application) {

    private val mContext = application
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    private val _quotes = MutableLiveData<List<QuoteRemote>>()
    private val _quotesLoadError = MutableLiveData<String?>()
    private val _loading = MutableLiveData<Boolean>()

    val quotes: LiveData<List<QuoteRemote>> = _quotes
    val quotesLoadError: LiveData<String?> = _quotesLoadError
    val loading: LiveData<Boolean> = _loading

    fun initDatabase(type: String){
        when(type){
            TYPE_ROOM -> {
                val dao = QuotesDatabase.getDatabase(mContext).quotesListDao()
                REPOSITORY = QuotesRepository(dao)
            }
        }
    }
    val allQuotes = QuotesRepository(QuotesDatabase.getDatabase(mContext).quotesListDao()).allQuotes

    fun insert(quote:QuotesListEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.insert(quote)
        }
    }

    fun delete(quote:QuotesListEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.delete(quote)
        }
    }

    fun refresh() {
        fetchQuotes()
    }

    private fun fetchQuotes() {
        _loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = quotesService.getQuotes()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _quotes.value = response.body()?.docs
                    _quotesLoadError.value = null
                    _loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
        _quotesLoadError.value = ""
        _loading.value = false
    }

    private fun onError(message: String) {
        _quotesLoadError.value = message
        _loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}