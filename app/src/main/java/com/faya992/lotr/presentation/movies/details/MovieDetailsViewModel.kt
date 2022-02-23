package com.faya992.lotr.presentation.movies.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faya992.lotr.R
import com.faya992.lotr.data.models.MovieRemote
import com.faya992.lotr.data.services.MoviesService
import kotlinx.coroutines.*


class MovieDetailsViewModel (private val moviesService: MoviesService) : ViewModel() {

    private val _movieName: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    private val _release: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    private val _runtime: MutableLiveData<Int> = MutableLiveData<Int>().apply { value = 0 }
    private val _budget: MutableLiveData<Int> = MutableLiveData<Int>().apply { value = 0 }
    private val _gross: MutableLiveData<Double> = MutableLiveData<Double>().apply { value = 0.0 }
    private val _awardNomination: MutableLiveData<Int> =
        MutableLiveData<Int>().apply { value = 0 }
    private val _awardWins: MutableLiveData<Int> = MutableLiveData<Int>().apply { value = 0 }
    private val _movieImage: MutableLiveData<Int> =
        MutableLiveData<Int>().apply { value = R.drawable.img_movie_4 }
    private val _movies = MutableLiveData<List<MovieRemote>>()
    private val _moviesLoadError = MutableLiveData<String?>()
    private val _loading = MutableLiveData<Boolean>()

    val movieName: LiveData<String> = _movieName
    val release: LiveData<String> = _release
    val runtime: LiveData<Int> = _runtime
    val budget: LiveData<Int> = _budget
    val gross: LiveData<Double> = _gross
    val awardNomination: LiveData<Int> = _awardNomination
    val awardWins: LiveData<Int> = _awardWins
    val movieImage: LiveData<Int> = _movieImage
    val movies: LiveData<List<MovieRemote>> = _movies
    val moviesLoadError: LiveData<String?> = _moviesLoadError
    val loading: LiveData<Boolean> = _loading

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun fetchQuotes(movie: Movies?) {
        _loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val id = when (movie) {
                Movies.MovieOne -> MoviesService.movieOne
                Movies.MovieTwo -> MoviesService.movieTwo
                else -> MoviesService.movieThree
            }
            val response = moviesService.getMovies(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _movies.value = response.body()?.docs
                    _moviesLoadError.value = null
                    _loading.value = false

                    _movieName.postValue(response.body()!!.docs[0].name)
                    _runtime.postValue(response.body()!!.docs[0].runtimeInMinutes)
                    _budget.postValue(response.body()!!.docs[0].budgetInMillions)
                    _gross.postValue(response.body()!!.docs[0].boxOfficeRevenueInMillions)
                    _awardNomination.postValue(response.body()!!.docs[0].academyAwardNominations)
                    _awardWins.postValue(response.body()!!.docs[0].academyAwardWins)

                    when (movie) {
                        Movies.MovieOne -> {
                            _movieImage.postValue(R.drawable.img_movie_4)
                            _release.postValue(Movies.MovieOne.release)
                        }
                        Movies.MovieTwo -> {
                            _movieImage.postValue(R.drawable.img_movie_5)
                            _release.postValue(Movies.MovieTwo.release)
                        }
                        else -> {
                            _movieImage.postValue(R.drawable.img_movie_6)
                            _release.postValue(Movies.MovieThree.release)
                        }
                    }

                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
        _moviesLoadError.value = ""
        _loading.value = false
    }

    private fun onError(message: String) {
        _moviesLoadError.value = message
        _loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}

