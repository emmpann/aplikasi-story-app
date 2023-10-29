package com.github.emmpann.aplikasistoryapp.core.component

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.StoryResponse
import com.github.emmpann.aplikasistoryapp.databinding.ItemStoryBinding

class StoryAdapter: RecyclerView.Adapter<StoryItemView>() {

    private val list: ArrayList<StoryResponse> = arrayListOf()

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
        fun onItemClicked(data: StoryResponse)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(stories: List<StoryResponse>) {
        list.addAll(stories)
        if (list.size > 1) notifyItemRangeChanged(0, list.lastIndex) else notifyItemInserted(0)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearList() {
        list.clear()
        notifyDataSetChanged() // improve to diffutils
    }
}