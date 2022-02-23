package com.faya992.lotr.presentation.books.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faya992.lotr.R
import com.faya992.lotr.data.models.BookRemote

class BookAdapter(var book: ArrayList<BookRemote>) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    fun updateCountries(newBook: List<BookRemote>) {
        book.clear()
        book.addAll(newBook)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = BookViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.cell_chapter, parent, false)
    )

    override fun getItemCount() = book.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(book[position], position)
    }

    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val chapterName = view.findViewById<TextView>(R.id.textViewChapter)
        private val chapterNumber = view.findViewById<TextView>(R.id.textViewChapterNumber)

        fun bind(chapter: BookRemote, position: Int) {
            chapterName.text = chapter.chapterName
            val a = position + 1
            chapterNumber.text = ("Chapter $a - ")
        }
    }
}