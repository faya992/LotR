package com.faya992.lotr.presentation.quotes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faya992.lotr.data.models.QuoteRemote
import com.faya992.lotr.databinding.CellQuoteBinding

class QuotesAdapter(var quotes: ArrayList<QuoteRemote>): RecyclerView.Adapter<QuotesAdapter.QuoteViewHolder>() {

    lateinit var listener: OnClickListener
    private val mDisplayList = ArrayList<QuoteRemote>()

    fun updateCountries(newQuotes: List<QuoteRemote>) {
        quotes.clear()
        quotes.addAll(newQuotes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): QuoteViewHolder {
        return QuoteViewHolder(parent, listener)
    }

    override fun getItemCount() = quotes.size

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bind(quotes[position])
    }

    class QuoteViewHolder(parent: ViewGroup, listener: OnClickListener): RecyclerView.ViewHolder(
        CellQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false).root) {

        private val binding = CellQuoteBinding.bind(itemView)
        private lateinit var currentquote: QuoteRemote

        init {
            itemView.setOnClickListener {
                listener.onAddClick(currentquote)
            }
            itemView.setOnLongClickListener {
                listener.onDeleteClick(currentquote)
                true
            }
        }

        fun bind(quote: QuoteRemote) {
            currentquote = quote
            binding.textViewQuote.text = quote.dialog
        }
    }

    interface OnClickListener {
        fun onAddClick(quote: QuoteRemote)
        fun onDeleteClick(quote: QuoteRemote)
    }

}