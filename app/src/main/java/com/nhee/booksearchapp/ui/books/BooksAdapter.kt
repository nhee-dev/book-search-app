package com.nhee.booksearchapp.ui.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nhee.booksearchapp.data.Book
import com.nhee.booksearchapp.databinding.ItemBookBinding

class BooksAdapter : ListAdapter<Book, BooksAdapter.ItemViewHolder>(diffUtil) {
    inner class ItemViewHolder(
        private val binding: ItemBookBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.tvTitle.text = book.title
            binding.tvAuthor.text = book.author
            binding.tvPublisher.text = book.publisher
            binding.tvDiscount.text = book.discount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksAdapter.ItemViewHolder {
        return ItemViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BooksAdapter.ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
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