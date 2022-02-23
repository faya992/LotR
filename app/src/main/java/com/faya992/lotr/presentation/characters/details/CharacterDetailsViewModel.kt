package com.faya992.lotr.presentation.characters.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faya992.lotr.data.models.CharacterRemote
import com.faya992.lotr.data.services.CharactersService
import kotlinx.coroutines.*

class CharacterDetailsViewModel (private val characterService: CharactersService): ViewModel() {

    private val _id: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    private val _race: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    private val _gender: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    private val _birth: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    private val _spouse: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    private val _death: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    private val _realm: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    private val _hair: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    private val _name: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    private val _wikiUrl: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
        private val _character = MutableLiveData<List<CharacterRemote>>()
    private val _characterLoadError = MutableLiveData<String?>()
    private val _loading = MutableLiveData<Boolean>()
    val id: LiveData<String> = _id
    val race: LiveData<String> = _race
    val gender: LiveData<String> = _gender
    val birth: LiveData<String> = _birth
    val spouse: LiveData<String> = _spouse
    val death: LiveData<String> = _death
    val realm: LiveData<String> = _realm
    val hair: LiveData<String> = _hair
    val name: LiveData<String> = _name
    val wikiUrl: LiveData<String> = _wikiUrl
    val character: LiveData<List<CharacterRemote>> = _character
    val characterLoadError: LiveData<String?> = _characterLoadError
    val loading: LiveData<Boolean> = _loading

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun fetchQuotes(id: String) {
        _loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val response = characterService.getCharacter(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _character.value = response.body()?.docs
                    _characterLoadError.value = null
                    _loading.value = false

                    _id.postValue(response.body()!!.docs[0]._id)
                    _race.postValue(response.body()!!.docs[0].race)
                    _gender.postValue(response.body()!!.docs[0].gender)
                    _birth.postValue(response.body()!!.docs[0].birth)
                    _spouse.postValue(response.body()!!.docs[0].spouse)
                    _death.postValue(response.body()!!.docs[0].death)
                    _realm.postValue(response.body()!!.docs[0].realm)
                    _hair.postValue(response.body()!!.docs[0].hair)
                    _name.postValue(response.body()!!.docs[0].name)
                    _wikiUrl.postValue(response.body()!!.docs[0].wikiUrl)

                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
        _characterLoadError.value = ""
        _loading.value = false
    }

    private fun onError(message: String) {
        _characterLoadError.value = message
        _loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}