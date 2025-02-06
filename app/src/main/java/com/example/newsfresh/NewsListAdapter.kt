package com.example.newsfresh

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsfresh.databinding.ItemNewsBinding

class NewsListAdapter(private val listener: NewsItemClicked) : RecyclerView.Adapter<NewsViewHolder>() {

    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem, listener)
    }

    fun updateNews(updatedNews: ArrayList<News>) {
        val diffCallback = NewsDiffCallback(items, updatedNews)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(updatedNews)

        diffResult.dispatchUpdatesTo(this)  // More efficient than notifyDataSetChanged()
    }
}

class NewsViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(news: News, listener: NewsItemClicked) {
        binding.title.text = news.title
        binding.author.text = news.author
        Glide.with(binding.root.context).load(news.imageUrl).into(binding.image)

        // Set click listener here to avoid adapterPosition issues
        binding.root.setOnClickListener {
            listener.onItemClicked(news)
        }
    }
}

// DiffUtil for better performance
class NewsDiffCallback(private val oldList: List<News>, private val newList: List<News>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title  // Assuming title is unique
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]  // Uses data class equals()
    }
}

interface NewsItemClicked {
    fun onItemClicked(item: News)
}
