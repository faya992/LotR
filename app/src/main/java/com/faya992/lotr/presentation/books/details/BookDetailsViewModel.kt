package com.faya992.lotr.presentation.books.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faya992.lotr.R
import com.faya992.lotr.data.models.BookRemote
import com.faya992.lotr.data.network.RetrofitFactory
import com.faya992.lotr.data.services.BooksService
import kotlinx.coroutines.*

class BookDetailsViewModel : ViewModel() {

    private val _bookName: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    private val _chapterName: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    private val _bookImage: MutableLiveData<Int> =
        MutableLiveData<Int>().apply { value = R.drawable.img_book_1 }
    private val _books = MutableLiveData<List<BookRemote>>()
    private val _booksLoadError = MutableLiveData<String?>()
    private val _loading = MutableLiveData<Boolean>()

    val bookName: LiveData<String> = _bookName
    val chapterName: LiveData<String> = _chapterName
    val bookImage: LiveData<Int> = _bookImage
    val books: LiveData<List<BookRemote>> = _books
    val booksLoadError: LiveData<String?> = _booksLoadError
    val loading: LiveData<Boolean> = _loading

    private val booksService = RetrofitFactory().getBooksService()
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun fetchBooks(book: Books?) {
        _loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val id = when (book) {
                Books.BookOne -> BooksService.bookOne
                Books.BookTwo -> BooksService.bookTwo
                else -> BooksService.bookThree
            }
            val response = booksService.getBooks(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _books.value = response.body()?.docs
                    _booksLoadError.value = null
                    _loading.value = false

                    _chapterName.postValue(response.body()!!.docs[0].chapterName)

                    when (book) {
                        Books.BookOne -> {
                            _bookImage.postValue(R.drawable.img_book_1)
                            _bookName.postValue(Books.BookOne.bookName)
                        }
                        Books.BookTwo -> {
                            _bookImage.postValue(R.drawable.img_book_2)
                            _bookName.postValue(Books.BookTwo.bookName)
                        }
                        else -> {
                            _bookImage.postValue(R.drawable.img_book_3)
                            _bookName.postValue(Books.BookThree.bookName)
                        }
                    }
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
        _booksLoadError.value = ""
        _loading.value = false
    }

    private fun onError(message: String) {
        _booksLoadError.value = message
        _loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
