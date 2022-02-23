package com.faya992.lotr.presentation.characters

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.faya992.lotr.databinding.FragmentCharactersBinding
import com.faya992.lotr.presentation.characters.adapters.CharactersAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private val charactersAdapter by lazy {
        CharactersAdapter(
            listener,
            arrayListOf()
        )
    }
    private val viewModel by viewModel<CharactersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel.refresh()

        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
            adapter = charactersAdapter
        }

        observeViewModel()

        binding.buttonFilter.setOnClickListener {
            binding.buttonFilter.isSelected = !binding.buttonFilter.isSelected
            if (binding.buttonFilter.isSelected == true) {
                binding.editTextSearch.visibility = View.INVISIBLE
                binding.linearLayoutRaces.visibility = View.VISIBLE
                binding.btnDwarf.setOnClickListener {
                    binding.btnDwarf.isSelected = !binding.btnDwarf.isSelected
                    viewModel.pressFilter("Dwarf", binding.btnDwarf.isSelected)
                }
                binding.btnElf.setOnClickListener {
                    binding.btnElf.isSelected = !binding.btnElf.isSelected
                    viewModel.pressFilter("Elf", binding.btnElf.isSelected)
                }
                binding.btnHobbit.setOnClickListener {
                    binding.btnHobbit.isSelected = !binding.btnHobbit.isSelected
                    viewModel.pressFilter("Hobbit", binding.btnHobbit.isSelected)
                }
                binding.btnHuman.setOnClickListener {
                    binding.btnHuman.isSelected = !binding.btnHuman.isSelected
                    viewModel.pressFilter("Human", binding.btnHuman.isSelected)
                }
            } else {
                binding.linearLayoutRaces.visibility = View.INVISIBLE
                binding.editTextSearch.visibility = View.VISIBLE
            }
        }

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                charactersAdapter.filter(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            charactersAdapter.notifyDataSetChanged()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val listener = CharactersAdapter.OnClickListener { character ->
        findNavController().navigate(
            CharactersFragmentDirections.actionNavigationCharactersToNavigationCharacterDetails(
                character._id
            )
        )
        viewModel.deletePressFilter()
    }

    fun observeViewModel() {
        viewModel.characters.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                binding.recyclerView.visibility = View.VISIBLE
                charactersAdapter.updateCountries(it)}
        })
        viewModel.charactersLoadError.observe(viewLifecycleOwner, Observer { isError ->
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