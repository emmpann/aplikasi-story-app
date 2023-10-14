package com.github.emmpann.aplikasistoryapp.feature.signup

import androidx.lifecycle.ViewModel
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor (private val repository: UserRepository) : ViewModel() {
    fun signup(name: String, email: String, password: String) = repository.signUp(name, email, password)

}