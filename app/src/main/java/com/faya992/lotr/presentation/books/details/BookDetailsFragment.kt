package com.faya992.lotr.presentation.books.details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.faya992.lotr.databinding.FragmentBookDetailsBinding
import com.faya992.lotr.domain.helpers.Keys
import com.faya992.lotr.presentation.books.details.adapter.BookAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookDetailsFragment : Fragment() {

    private var _binding: FragmentBookDetailsBinding? = null
    private val binding get() = _binding!!
    private val bookAdapter = BookAdapter(arrayListOf())
    private val viewModel by viewModel<BookDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBookDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bookAdapter
        }

        observeViewModel()
        viewModel.fetchBooks(arguments?.get(Keys.Book.value) as? Books)

        binding.swipeRefreshLayout.setOnRefreshListener {
            observeViewModel()
            viewModel.fetchBooks(arguments?.get(Keys.Book.value) as? Books)
            binding.swipeRefreshLayout.isRefreshing = false
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun observeViewModel() {
        viewModel.books.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                binding.recyclerView.visibility = View.VISIBLE
                bookAdapter.updateCountries(it)
                configureLayout()
            }
        })

        viewModel.booksLoadError.observe(viewLifecycleOwner, Observer { isError ->
            binding.textViewNothing.visibility = if (isError == "") View.GONE else View.VISIBLE
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.textViewNothing.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                }
            }
        })
    }

    private fun configureLayout() {
        viewModel.bookName.observe(viewLifecycleOwner, Observer {
            binding.textViewBook.text = it.toString()
        })
        viewModel.bookImage.observe(viewLifecycleOwner, Observer {
            binding.imageView.setImageResource(it)
        })
    }
}