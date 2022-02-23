package com.faya992.lotr.presentation.quotes.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.faya992.lotr.R

class QuoteDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = QuoteDetailsFragment()
    }

    private lateinit var viewModel: QuoteDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quote_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuoteDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}