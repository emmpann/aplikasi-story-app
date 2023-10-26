package com.github.emmpann.aplikasistoryapp.feature.detail

import androidx.lifecycle.ViewModel
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.story.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val storyRepository: StoryRepository) : ViewModel() {


    fun getStoryDetail(id: String) = storyRepository.getStoryDetail(id)
}