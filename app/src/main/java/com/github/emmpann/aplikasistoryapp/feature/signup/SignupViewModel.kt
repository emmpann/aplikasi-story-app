package com.github.emmpann.aplikasistoryapp.feature.signup

import androidx.lifecycle.ViewModel
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.user.UserRepository

class SignupViewModel(private val repository: UserRepository) : ViewModel() {
    fun signup(name: String, email: String, password: String) = repository.signUp(name, email, password)

}