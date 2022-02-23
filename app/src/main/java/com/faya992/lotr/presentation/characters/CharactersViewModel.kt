package com.faya992.lotr.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faya992.lotr.data.models.CharacterRemote
import com.faya992.lotr.data.services.CharactersService
import kotlinx.coroutines.*

class CharactersViewModel (private val charactersService: CharactersService): ViewModel() {

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    private val _characters = MutableLiveData<List<CharacterRemote>>()
    private val _charactersDisplay = MutableLiveData<List<CharacterRemote>>()
    private val _charactersLoadError = MutableLiveData<String?>()
    private val _loading = MutableLiveData<Boolean>()
    private val _filter = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf()
    }

    val characters: LiveData<List<CharacterRemote>> = _charactersDisplay
    val charactersLoadError: LiveData<String?> = _charactersLoadError
    val loading: LiveData<Boolean> = _loading

    fun refresh() {
        fetchQuotes()
    }

    private fun fetchQuotes() {
        _loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = charactersService.getCharacters()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _characters.value = response.body()?.docs
                    _charactersDisplay.value = _characters.value
                    _charactersLoadError.value = null
                    _loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
        _charactersLoadError.value = ""
        _loading.value = false
    }

    fun deletePressFilter(){
        _filter.value?.clear()
    }

    fun pressFilter(type: String, isSelected: Boolean) {
        if (isSelected) {
            _filter.value?.add(type)
        } else {
            _filter.value?.remove(type)
        }
        if (_filter.value?.isEmpty() == true) {
            _charactersDisplay.postValue(_characters.value ?: ArrayList())
            return
        }
        _charactersDisplay.postValue(_characters.value?.filter { _filter.value?.contains(it.race) ?: false }
            ?.toMutableList())
    }

    private fun onError(message: String) {
        _charactersLoadError.value = message
        _loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}