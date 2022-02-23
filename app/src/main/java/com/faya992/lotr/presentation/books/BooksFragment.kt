package com.faya992.lotr.presentation.books


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.faya992.lotr.R
import com.faya992.lotr.databinding.FragmentBooksBinding

class BooksFragment : Fragment() {

    private var _binding: FragmentBooksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBooksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonOne.setOnClickListener { showDetail(it) }
        binding.buttonTwo.setOnClickListener { showDetail(it) }
        binding.buttonThree.setOnClickListener { showDetail(it) }

        return root
    }

    private fun showDetail(sender: View) {
        sender.findNavController().navigate(R.id.action_navigation_books_to_bookDetailsFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}