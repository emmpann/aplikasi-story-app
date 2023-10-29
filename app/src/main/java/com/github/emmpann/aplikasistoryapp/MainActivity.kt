package com.github.emmpann.aplikasistoryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.github.emmpann.aplikasistoryapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).navController
        setStartDestination()
    }

    private fun setStartDestination() {
        mainViewModel.session.observe(this) {
            navController.navInflater.inflate(R.navigation.main_navigation).apply {
                setStartDestination(if (it.token.isNotEmpty() && it.isLogin) R.id.homeFragment else R.id.loginFragment)
                navController.graph = this
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}