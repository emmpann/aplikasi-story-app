package com.github.emmpann.aplikasistoryapp.core.component

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.Story
import com.github.emmpann.aplikasistoryapp.databinding.ItemStoryBinding

class StoryItemView(private val binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Story) {
        binding.apply {
            titleTextView.text = data.name
            descTextView.text = data.description
            Glide.with(binding.root.context)
                .load(data.photoUrl)
                .into(imageView)

        }
    }
}
