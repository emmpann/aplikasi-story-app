package com.github.emmpann.aplikasistoryapp.feature.detail

import androidx.lifecycle.ViewModel
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.StoryRepository

class DetailViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun getStoryDetail(id: String) = storyRepository.getStoryDetail(id)
}