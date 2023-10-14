package com.github.emmpann.aplikasistoryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.github.emmpann.aplikasistoryapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController
        val navInflater = navController.navInflater
        val navGraph = navInflater.inflate(R.navigation.main_navigation)

        mainViewModel.getSession().observe(this) {
            if (it.isLogin) {
                Log.d("MainActivity", "home")
                navGraph.setStartDestination(R.id.homeFragment)
                navHostFragment.navController.graph = navGraph
            } else {
                Log.d("MainActivity", "welcome")
                navGraph.setStartDestination(R.id.welcomeFragment)
                navHostFragment.navController.graph = navGraph
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}