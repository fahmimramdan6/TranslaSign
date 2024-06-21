package com.dicoding.translasign.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.translasign.R
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView

class DictionaryFragment : Fragment() {

    private val viewModel: DictionaryViewModel by viewModels()
    private lateinit var dictionaryAdapter: DictionaryAdapter
    private lateinit var searchBar: SearchBar
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dictionary, container, false)

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        dictionaryAdapter = DictionaryAdapter(emptyList())
        recyclerView.adapter = dictionaryAdapter

        viewModel.filteredItems.observe(viewLifecycleOwner, Observer { items ->
            dictionaryAdapter.updateList(items)
        })

        searchBar = view.findViewById(R.id.searchBar)
        searchView = view.findViewById(R.id.searchView)

        searchBar.setOnClickListener {
            searchView.show()
        }

        searchView.editText.addTextChangedListener {
            val query = it.toString()
            searchBar.setText(searchView.text.toString())
            viewModel.searchDictionary(query)
        }

        searchView.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                searchView.hide()
            }
        }

        return view
    }
}
