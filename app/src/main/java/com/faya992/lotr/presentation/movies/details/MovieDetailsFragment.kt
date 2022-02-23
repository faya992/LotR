package com.faya992.lotr.presentation.movies.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.faya992.lotr.databinding.FragmentMovieDetailsBinding
import com.faya992.lotr.domain.helpers.Keys

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movieDetailsViewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        observeViewModel()
        movieDetailsViewModel.fetchQuotes(arguments?.get(Keys.Movie.value) as? Movies)

        binding.swipeRefreshLayout.setOnRefreshListener {
            observeViewModel()
            movieDetailsViewModel.fetchQuotes(arguments?.get(Keys.Movie.value) as? Movies)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        return root
    }

    private fun observeViewModel() {
        movieDetailsViewModel.movies.observe(viewLifecycleOwner, Observer {countries ->
            countries?.let {
                binding.linearLayoutMovieDetails.visibility = View.VISIBLE
                configureLayout() }
        })
        movieDetailsViewModel.moviesLoadError.observe(viewLifecycleOwner, Observer { isError ->
            binding.textViewNothing.visibility = if(isError == "") View.GONE else View.VISIBLE
        })
        movieDetailsViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                binding.progressBar.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    binding.textViewNothing.visibility = View.GONE
                    binding.linearLayoutMovieDetails.visibility = View.GONE
                }
            }
        })

    }

    private fun configureLayout() {
        movieDetailsViewModel.movieName.observe(viewLifecycleOwner, Observer {
            binding.textViewMovie.text = it
        })
        movieDetailsViewModel.release.observe(viewLifecycleOwner, Observer {
            binding.textViewRelease.text = it
        })
        movieDetailsViewModel.runtime.observe(viewLifecycleOwner, Observer {
            binding.textViewRuntime.text = it.toString()
        })
        movieDetailsViewModel.budget.observe(viewLifecycleOwner, Observer {
            binding.textViewBudget.text = it.toString()
        })
        movieDetailsViewModel.gross.observe(viewLifecycleOwner, Observer {
            binding.textViewGross.text = it.toString()
        })
        movieDetailsViewModel.awardNomination.observe(viewLifecycleOwner, Observer {
            binding.textViewAwardsNomination.text = it.toString()
        })
        movieDetailsViewModel.awardWins.observe(viewLifecycleOwner, Observer {
            binding.textViewAwardsWins.text = it.toString()
        })
        movieDetailsViewModel.movieImage.observe(viewLifecycleOwner, Observer {
            binding.imageView.setImageResource(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}