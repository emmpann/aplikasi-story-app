package com.github.emmpann.aplikasistoryapp.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.user.UserRepository
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.ResultApi
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.user.RequestLoginResponse
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.user.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _loginResponse = MutableLiveData<ResultApi<RequestLoginResponse>>()
    val loginResponse: LiveData<ResultApi<RequestLoginResponse>> get() = _loginResponse

    fun login(email: String, password: String) = viewModelScope.launch {
        userRepository.login(email, password).collect {
            _loginResponse.value = it
        }
    }

    fun saveSession(user: UserResponse) {
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }
}