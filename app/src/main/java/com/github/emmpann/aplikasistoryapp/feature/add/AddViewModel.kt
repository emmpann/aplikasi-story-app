package com.github.emmpann.aplikasistoryapp.feature.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.story.StoryRepository
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.ResultApi
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.RequestUploadStoryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val storyRepository: StoryRepository) : ViewModel() {

    private val _addStoryResponse = MutableLiveData<ResultApi<RequestUploadStoryResponse>>()
    val addStoryResponse: LiveData<ResultApi<RequestUploadStoryResponse>> get() = _addStoryResponse

    fun uploadStory(
        file: File,
        description: String,
        lat: Float, lon: Float,
    ) =
        viewModelScope.launch {
            storyRepository.uploadStory(
                file,
                description,
                lat, lon
            ).collect {
                _addStoryResponse.value = it
            }
        }
}