package com.nhee.booksearchapp.ui.recentsearchwords

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nhee.booksearchapp.R
import com.nhee.booksearchapp.databinding.FragmentRecentSearchWordsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecentSearchWordsFragment : Fragment() {

    private val viewModel by viewModels<RecentSearchWordsViewModel>()

    private lateinit var viewDataBinding: FragmentRecentSearchWordsBinding

    private lateinit var searchWordsAdapter: RecentSearchWordsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentRecentSearchWordsBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@RecentSearchWordsFragment)
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setObserver()
    }

    private fun setRecyclerView() {
        searchWordsAdapter = RecentSearchWordsAdapter()

        viewDataBinding.rvRecentSearchWords.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchWordsAdapter
        }
    }

    private fun setObserver() {
        viewModel.searchWordsList.observe(viewLifecycleOwner) { words ->
            // Update the cached copy of the words in the adapter.
            searchWordsAdapter.submitList(words)
        }
    }
}