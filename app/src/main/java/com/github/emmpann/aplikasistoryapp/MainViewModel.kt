package com.github.emmpann.aplikasistoryapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.github.emmpann.aplikasistoryapp.core.data.local.database.UserModel
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.user.UserRepository

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}