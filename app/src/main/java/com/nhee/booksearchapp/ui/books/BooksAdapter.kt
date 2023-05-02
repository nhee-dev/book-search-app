package com.nhee.booksearchapp.ui.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nhee.booksearchapp.data.Book
import com.nhee.booksearchapp.databinding.ItemBookBinding

class BooksAdapter : ListAdapter<Book, BooksAdapter.ItemViewHolder>(diffUtil) {
    inner class ItemViewHolder(
        private val binding: ItemBookBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.apply {
                tvTitle.text = book.title
                tvAuthor.text = book.author
                tvPublisher.text = book.publisher
                tvDiscount.text = book.discount.toString()
                Glide.with(root)
                    .load(book.image)
                    .into(ivBook)

                layoutBookInfo.setOnClickListener {
                    bookClickListener.onClick(it, layoutPosition, book)
                }
                btnBookmark.setOnClickListener {
                    bookmarkClickListener.onClick(it, layoutPosition, book)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksAdapter.ItemViewHolder {
        return ItemViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BooksAdapter.ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int, item: Book)
    }

    private lateinit var bookClickListener: ItemClickListener
    fun setBookClickListener(bookClickListener: ItemClickListener) {
        this.bookClickListener = bookClickListener
    }

    private lateinit var bookmarkClickListener: ItemClickListener
    fun setBookmarkClickListener(bookmarkClickListener: ItemClickListener) {
        this.bookmarkClickListener = bookmarkClickListener
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}