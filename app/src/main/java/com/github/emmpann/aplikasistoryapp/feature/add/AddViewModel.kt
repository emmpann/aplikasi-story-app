package com.github.emmpann.aplikasistoryapp.feature.add

import androidx.lifecycle.ViewModel
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.story.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val storyRepository: StoryRepository) : ViewModel() {
    fun uploadStory(file: File, description: String) = storyRepository.uploadStory(file, description)
}