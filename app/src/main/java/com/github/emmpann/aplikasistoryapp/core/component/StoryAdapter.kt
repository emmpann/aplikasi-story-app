package com.github.emmpann.aplikasistoryapp.core.component

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.ListStoryItem
import com.github.emmpann.aplikasistoryapp.databinding.ItemStoryBinding

class StoryAdapter: RecyclerView.Adapter<StoryItemView>() {

    private val list: ArrayList<ListStoryItem> = arrayListOf()

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryItemView {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryItemView(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: StoryItemView, position: Int) {
        val data = list[position]
        holder.bind(data)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(data) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListStoryItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(stories: List<ListStoryItem>) {
        list.addAll(stories)
        if (list.size > 1) notifyItemRangeChanged(0, list.lastIndex) else notifyItemInserted(0)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }
}