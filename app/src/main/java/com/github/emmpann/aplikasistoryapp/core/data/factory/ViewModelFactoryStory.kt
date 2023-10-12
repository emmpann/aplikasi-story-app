package com.github.emmpann.aplikasistoryapp.core.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.emmpann.aplikasistoryapp.core.data.di.Injection
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.story.StoryRepository
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.user.UserRepository
import com.github.emmpann.aplikasistoryapp.feature.add.AddViewModel
import com.github.emmpann.aplikasistoryapp.feature.detail.DetailViewModel
import com.github.emmpann.aplikasistoryapp.feature.home.HomeViewModel

class ViewModelFactoryStory private constructor(private val userRepository: UserRepository, private val storyRepository: StoryRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) return HomeViewModel(userRepository, storyRepository) as T
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) return DetailViewModel(storyRepository) as T
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) return AddViewModel(storyRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        private var instance: ViewModelFactoryStory? = null
        fun getInstance(context: Context): ViewModelFactoryStory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactoryStory(Injection.provideUserRepository(context), Injection.provideStoryRepository(context))
            }
    }
}