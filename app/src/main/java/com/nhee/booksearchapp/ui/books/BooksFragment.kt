package com.nhee.booksearchapp.ui.books

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nhee.booksearchapp.databinding.FragmentBooksBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BooksFragment : Fragment() {

    private val viewModel by viewModels<BooksViewModel>()

    private lateinit var viewDataBinding: FragmentBooksBinding

    private lateinit var booksAdapter: BooksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentBooksBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
            setLifecycleOwner(this@BooksFragment)
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBookSearchBtn()
        setupNavigation()
        setupRecyclerView()
        setObserver()
    }

    private fun setupBookSearchBtn() {
        viewDataBinding.btnBookSearch.apply {
            setOnClickListener {
                if (viewModel.searchWords.value.equals("")) {
                    Toast.makeText(requireContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.searchBooks()
                }
            }
        }
    }

    private fun setupNavigation() {
        viewDataBinding.apply {
            tvBookmarks.setOnClickListener {
                val action = BooksFragmentDirections.actionBooksFragmentToBookmarksFragment()
                findNavController().navigate(action)
            }
            tvRecentSearchWords.setOnClickListener {
                val action = BooksFragmentDirections.actionBooksFragmentToRecentSearchWordsFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun setupRecyclerView() {
        booksAdapter = BooksAdapter()

        viewDataBinding.rvBooks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = booksAdapter
        }
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.books.collect {
                booksAdapter.submitList(it)
            }
        }
    }
}