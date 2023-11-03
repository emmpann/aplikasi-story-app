package com.github.emmpann.aplikasistoryapp.feature.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.emmpann.aplikasistoryapp.R
import com.github.emmpann.aplikasistoryapp.core.component.LoadingStateAdapter
import com.github.emmpann.aplikasistoryapp.core.component.StoryAdapter
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.StoryResponse
import com.github.emmpann.aplikasistoryapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var storyAdapter: StoryAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(view)
        setupAction(view)
        setupItem()
    }

    private fun setupToolbar(view: View) {

        binding.toolbar.title = getString(R.string.app_name)

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_map -> {
                    view.findNavController().navigate(R.id.action_homeFragment_to_mapsFragment)
                    true
                }

                R.id.menu_language_change -> {
                    startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                    true
                }

                R.id.menu_logout -> {
                    //logout
                    showDialog()
                    true
                }

                else -> {
                    true
                }
            }
        }
    }

    private fun setupAction(view: View) {
        binding.addStoryButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }
    }

    private fun setupItem() {
        binding.rvStory.apply {
            storyAdapter = StoryAdapter()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    storyAdapter.retry()
                }
            )
            storyAdapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
                override fun onItemClicked(data: StoryResponse) {
                    val toDetailFragment =
                        HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                    toDetailFragment.storyId = data.id
                    findNavController().navigate(toDetailFragment)
                }
            })
        }

        viewModel.stories.observe(viewLifecycleOwner) { result ->
            storyAdapter.submitData(lifecycle, result)
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.logout))
            setMessage(getString(R.string.are_you_sure))
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.logout()
            }
            create()
            show()
        }
    }
}