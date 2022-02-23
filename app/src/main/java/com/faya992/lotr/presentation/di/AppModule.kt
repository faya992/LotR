package com.faya992.lotr.presentation.di

import com.faya992.lotr.presentation.books.details.BookDetailsViewModel
import com.faya992.lotr.presentation.characters.CharactersViewModel
import com.faya992.lotr.presentation.characters.details.CharacterDetailsViewModel
import com.faya992.lotr.presentation.movies.details.MovieDetailsViewModel
import com.faya992.lotr.presentation.quotes.QuotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<CharactersViewModel>{
        CharactersViewModel()
    }

    viewModel<CharacterDetailsViewModel>{
        CharacterDetailsViewModel()
    }

    viewModel<BookDetailsViewModel>{
        BookDetailsViewModel()
    }

    viewModel<QuotesViewModel>{
        QuotesViewModel(application = get())
    }

    viewModel<MovieDetailsViewModel>{
        MovieDetailsViewModel()
    }


}