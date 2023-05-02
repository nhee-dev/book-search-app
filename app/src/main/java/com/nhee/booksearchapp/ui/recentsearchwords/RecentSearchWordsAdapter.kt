package com.nhee.booksearchapp.ui.recentsearchwords

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.nhee.booksearchapp.data.searchwords.SearchWord
import com.nhee.booksearchapp.databinding.ItemRecentSearchWordBinding

class RecentSearchWordsAdapter: ListAdapter<SearchWord, RecentSearchWordsAdapter.ItemViewHolder>(diffUtil) {

    inner class ItemViewHolder(
        private val binding: ItemRecentSearchWordBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(recentSearchWord: SearchWord) {
            binding.tvRecentSearchWords.text = recentSearchWord.word
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemRecentSearchWordBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SearchWord>() {
            override fun areItemsTheSame(oldItem: SearchWord, newItem: SearchWord): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SearchWord, newItem: SearchWord): Boolean {
                return oldItem.word == newItem.word
            }
        }
    }
}