package com.github.emmpann.aplikasistoryapp.core.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.emmpann.aplikasistoryapp.MainViewModel
import com.github.emmpann.aplikasistoryapp.core.data.di.Injection
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.user.UserRepository
import com.github.emmpann.aplikasistoryapp.feature.login.LoginViewModel
import com.github.emmpann.aplikasistoryapp.feature.signup.SignupViewModel

class ViewModelFactoryUser private constructor(private val userRepository: UserRepository) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) return SignupViewModel(userRepository) as T
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) return LoginViewModel(userRepository) as T
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) return MainViewModel(userRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactoryUser? = null
        fun getInstance(context: Context): ViewModelFactoryUser =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactoryUser(Injection.provideUserRepository(context))
            }
    }
}