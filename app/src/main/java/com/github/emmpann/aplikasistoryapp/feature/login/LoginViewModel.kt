package com.github.emmpann.aplikasistoryapp.feature.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.user.UserRepository
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.ResultApi
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.user.RequestLoginResponse
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private var _loginResponse = MutableLiveData<ResultApi<RequestLoginResponse>>()
    val loginResponse = _loginResponse
//    fun login(email: String, password: String) {
//        _loginResponse = repository.login(email, password)
//    }

    fun login(email: String, password: String) = repository.login(email, password)

    fun saveSession(user: User) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}