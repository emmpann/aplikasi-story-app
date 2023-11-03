package com.github.emmpann.aplikasistoryapp.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.story.StoryRepository
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.ResultApi
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.StoryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val storyRepository: StoryRepository) :
    ViewModel() {

    private val storyId = MutableLiveData<String>()

    val storyDetail: LiveData<ResultApi<StoryResponse>> = storyId.switchMap {
        storyRepository.getStoryDetail(it).asLiveData()
    }

    fun setStoryId(storyId: String) {
        this.storyId.value = storyId
    }
}