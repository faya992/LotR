package com.faya992.lotr.presentation.characters.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faya992.lotr.R
import com.faya992.lotr.data.models.CharacterRemote
import com.faya992.lotr.presentation.characters.Races
import kotlin.collections.ArrayList

class CharactersAdapter(private val onClickListener: OnClickListener, var characters: ArrayList<CharacterRemote>) :
    RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    private val mDisplayList = ArrayList<CharacterRemote>()

    fun filter(query: String) {
        mDisplayList.clear()

        if (query.isEmpty()) {
            mDisplayList.addAll(characters)
            notifyDataSetChanged()
            return
        }

        mDisplayList.addAll(characters.filter {
            it.name.contains(query, true)
        })
        notifyDataSetChanged()
    }

    fun updateCountries(newCharacters: List<CharacterRemote>) {
        characters.clear()
        characters.addAll(newCharacters)
        filter("")
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = CharacterViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.cell_character, parent, false)
    )

    override fun getItemCount() = mDisplayList.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(mDisplayList[position])
        holder.itemView.setOnClickListener {
            onClickListener.onClick(mDisplayList[position])
        }
    }

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.textViewName)
        private val race = view.findViewById<TextView>(R.id.textViewRace)
        private val gender = view.findViewById<TextView>(R.id.textViewGender)
        private val image = view.findViewById<ImageView>(R.id.imageViewCharacter)
        private val url = "https://avatarko.ru/img/kartinka/28/gnom_27079.jpg"

        fun bind(character: CharacterRemote) {
            name.text = character.name
            race.text = character.race
            gender.text = character.gender
            Glide
                .with(itemView.context)
                .load(getUrl(character.race))
                .into(image)
    }

        private fun getUrl(race: String): String {
            return when (race) {
                "Human" -> Races.Human.url
                "Dwarf" -> Races.Dwarf.url
                "Elf" -> Races.Elf.url
                "Orc" -> Races.Orc.url
                "Hobbit" -> Races.Hobbit.url
                else -> Races.Maiar.url
            }
        }
    }

    class OnClickListener(val clickListener: (character: CharacterRemote) -> Unit) {
        fun onClick(character: CharacterRemote) = clickListener(character)
    }
}