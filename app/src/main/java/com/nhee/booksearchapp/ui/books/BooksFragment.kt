package com.nhee.booksearchapp.ui.books

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nhee.booksearchapp.data.Book
import com.nhee.booksearchapp.data.Result
import com.nhee.booksearchapp.databinding.FragmentBooksBinding
import com.nhee.booksearchapp.ui.dialog.ErrorDialog
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
                    booksAdapter.submitList(listOf())
                    viewModel.searchBooks(true)
                }
            }
        }
    }

    private fun setupNavigation() {
        viewDataBinding.apply {
//            tvBookmarks.setOnClickListener {
//                val action = BooksFragmentDirections.actionBooksFragmentToBookmarksFragment()
//                findNavController().navigate(action)
//            }
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
            adapter = booksAdapter.apply {
                setBookClickListener(object : BooksAdapter.ItemClickListener {
                    override fun onClick(view: View, position: Int, item: Book) {
                        val action = BooksFragmentDirections.actionBooksFragmentToWebBrowserFragment(item.link)
                        findNavController().navigate(action)
                    }
                })

//                setBookmarkClickListener(object  : BooksAdapter.ItemClickListener {
//                    override fun onClick(view: View, position: Int, item: Book) {
//                        val msg: String = position.toString() + "번째 책 북마크!"
//                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
//                    }
//                })
            }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager?)!!
                            .findLastCompletelyVisibleItemPosition()

                    val itemTotalCount = recyclerView.adapter!!.itemCount
                    val itemLastIndex = itemTotalCount - 1
                    if (lastVisibleItemPosition == itemLastIndex) {
                        viewModel.searchBooks(false)
                    }
                }
            })
        }
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.books.collect {
                val newList = booksAdapter.currentList.toMutableList()
                newList.addAll(it)
                booksAdapter.submitList(newList)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.booksSearchResults.collect {
                if (it is Result.Loading) {
                    showProgressBar()
                } else {
                    hideProgressBar()
                }

                if(it is Result.Error) {
                    ErrorDialog(it.exception?.message).show(requireActivity().supportFragmentManager, "ErrorDialog")
                }
            }
        }
    }

    private fun showProgressBar() {
        viewDataBinding.pbBookSearch.visibility = View.VISIBLE
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun hideProgressBar() {
        viewDataBinding.pbBookSearch.visibility = View.GONE
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}