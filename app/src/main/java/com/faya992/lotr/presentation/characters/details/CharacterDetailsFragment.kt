package com.faya992.lotr.presentation.characters.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.faya992.lotr.R
import com.faya992.lotr.databinding.FragmentCharacterDetailsBinding
import com.faya992.lotr.presentation.characters.Races
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CharacterDetailsFragment : Fragment() {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!
    private val characterDetailsViewModel by viewModel<CharacterDetailsViewModel>()
    private val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        observeViewModel()
        characterDetailsViewModel.fetchQuotes(args.characterId)

            characterDetailsViewModel.wikiUrl.observe(viewLifecycleOwner, Observer {
                binding.textViewUrl.visibility = if(it == "") View.GONE else View.VISIBLE
                val url = it
                binding.textViewUrl.setOnClickListener {
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse(url.toString())
                    if (openURL.resolveActivity(requireActivity().packageManager) != null) {
                        startActivity(openURL)
                    } else {
                        Log.d("ImplicitIntents", "Can't handle this intent! $url")
                    }
                }
            })

        binding.swipeRefreshLayout.setOnRefreshListener {
            observeViewModel()
            characterDetailsViewModel.fetchQuotes(args.characterId)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        return root
    }

    private fun observeViewModel() {
        characterDetailsViewModel.character.observe(viewLifecycleOwner, Observer {countries ->
            countries?.let {
                configureLayout() }
        })
        characterDetailsViewModel.characterLoadError.observe(viewLifecycleOwner, Observer { isError ->
            binding.textViewNothing.visibility = if(isError == null) View.GONE else View.VISIBLE
        })
        characterDetailsViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                binding.progressBar.visibility = if(it) View.VISIBLE else View.GONE
            }
        })
    }

    private fun configureLayout() {
        characterDetailsViewModel.name.observe(viewLifecycleOwner, Observer {
            binding.textViewName.text = it
        })
        characterDetailsViewModel.race.observe(viewLifecycleOwner, Observer {
            binding.cardViewRace.visibility = if (it == "") View.GONE else View.VISIBLE
            binding.textViewRace.text = it
            Glide
                .with(this)
                .load(getUrl(it.lowercase(Locale.getDefault())))
                .error(R.drawable.launch_screen)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageViewCharacter)
        })
        characterDetailsViewModel.birth.observe(viewLifecycleOwner, Observer {
            binding.cardViewBirth.visibility = if (it == "") View.GONE else View.VISIBLE
            binding.textViewBirth.text = it.toString()
        })
        characterDetailsViewModel.death.observe(viewLifecycleOwner, Observer {
            binding.cardViewDeath.visibility = if (it == "") View.GONE else View.VISIBLE
            binding.textViewDeath.text = it.toString()
        })
        characterDetailsViewModel.hair.observe(viewLifecycleOwner, Observer {
            binding.cardViewHair.visibility = if (it == "") View.GONE else View.VISIBLE
            binding.textViewHair.text = it.toString()
        })
        characterDetailsViewModel.spouse.observe(viewLifecycleOwner, Observer {
            binding.cardViewSpouse.visibility = if (it == "") View.GONE else View.VISIBLE
            binding.textViewSpouse.text = it.toString()
        })
        characterDetailsViewModel.gender.observe(viewLifecycleOwner, Observer {
            binding.cardViewGender.visibility = if (it == "") View.GONE else View.VISIBLE
            binding.textViewGender.text = it.toString()
        })
        characterDetailsViewModel.realm.observe(viewLifecycleOwner, Observer {
            binding.cardViewRealm.visibility = if (it == "") View.GONE else View.VISIBLE
            binding.textViewRealm.text = it.toString()
        })
    }

    private fun getUrl(race: String): String {
        return when (race) {
            "human" -> Races.Human.url
            "dwarf" -> Races.Dwarf.url
            "elf" -> Races.Elf.url
            "orc" -> Races.Orc.url
            "hobbit" -> Races.Hobbit.url
            else -> Races.Maiar.url
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}