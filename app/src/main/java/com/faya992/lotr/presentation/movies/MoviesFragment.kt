package com.faya992.lotr.presentation.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.faya992.lotr.R
import com.faya992.lotr.databinding.FragmentMoviesBinding
import com.faya992.lotr.domain.helpers.Keys
import com.faya992.lotr.presentation.movies.details.Movies

class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonOne.setOnClickListener { showDetail(it) }
        binding.buttonTwo.setOnClickListener { showDetail(it) }
        binding.buttonThree.setOnClickListener { showDetail(it) }

        return root
    }

    private fun showDetail(sender: View) {
        val movie = when (sender.tag) {
            "1" -> Movies.MovieOne
            "2" -> Movies.MovieTwo
            else -> Movies.MovieThree
        }

        sender.findNavController().navigate(R.id.action_navigation_movies_to_navigation_movie_details,
            Bundle().apply {
                this.putSerializable(Keys.Movie.value, movie) } )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}