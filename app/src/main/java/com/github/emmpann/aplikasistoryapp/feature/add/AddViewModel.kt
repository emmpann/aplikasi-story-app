package com.github.emmpann.aplikasistoryapp.feature.add

import androidx.lifecycle.ViewModel
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.story.StoryRepository
import java.io.File

class AddViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun uploadStory(file: File, description: String) = storyRepository.uploadStory(file, description)
}