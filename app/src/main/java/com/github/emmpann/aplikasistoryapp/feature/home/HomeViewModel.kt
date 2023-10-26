package com.github.emmpann.aplikasistoryapp.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.story.StoryRepository
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.user.UserRepository
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.ResultApi
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val storyRepository: StoryRepository,
) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }

    val stories: LiveData<ResultApi<List<Story>>> = storyRepository.getAllStory()
    fun getAllStory() = storyRepository.getAllStory()
}