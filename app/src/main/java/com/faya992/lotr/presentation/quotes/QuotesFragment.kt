package com.faya992.lotr.presentation.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.Observer
import com.faya992.lotr.data.models.QuoteRemote


import com.faya992.lotr.databinding.FragmentQuotesBinding
import com.faya992.lotr.domain.models.mapToModel
import com.faya992.lotr.domain.models.mapToRoomModel
import com.faya992.lotr.domain.helpers.TYPE_ROOM
import com.faya992.lotr.presentation.quotes.adapters.QuotesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuotesFragment : Fragment() {

    private val viewModel by viewModel<QuotesViewModel>()
    private var _binding: FragmentQuotesBinding? = null
    private val binding get() = _binding!!
    private val quotesAdapter by lazy {
        QuotesAdapter(arrayListOf()).apply {
            listener = object : QuotesAdapter.OnClickListener {
                override fun onDeleteClick(quote: QuoteRemote) {
                    viewModel.delete(quote.mapToRoomModel())
                }
                override fun onAddClick(quote: QuoteRemote) {
                    viewModel.insert(quote.mapToRoomModel())
                    binding.buttonFilter.isSelected = !binding.buttonFilter.isSelected
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentQuotesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        getFromNetwork()

        viewModel.initDatabase(TYPE_ROOM)
        viewModel.refresh()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = quotesAdapter
        }


        binding.buttonFilter.setOnClickListener {
            binding.buttonFilter.isSelected = !binding.buttonFilter.isSelected
            if (binding.buttonFilter.isSelected == true) {
                getFromRoom()
            } else {
                getFromNetwork()
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            quotesAdapter.notifyDataSetChanged()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getFromRoom() {
        viewModel.allQuotes.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                val listRoom = it.map { it.mapToModel() }
                binding.recyclerView.visibility = View.VISIBLE
                quotesAdapter.updateCountries(listRoom)
            }
        })
    }


    fun getFromNetwork() {
        viewModel.quotes.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                binding.recyclerView.visibility = View.VISIBLE
                quotesAdapter.updateCountries(it)
            }
        })

        viewModel.quotesLoadError.observe(viewLifecycleOwner, Observer { isError ->
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


}