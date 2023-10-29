package com.github.emmpann.aplikasistoryapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.user.UserRepository
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.user.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {
    val session: LiveData<UserResponse> = repository.getSession().asLiveData()
}