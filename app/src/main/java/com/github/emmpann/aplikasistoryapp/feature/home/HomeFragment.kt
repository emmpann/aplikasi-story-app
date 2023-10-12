package com.github.emmpann.aplikasistoryapp.feature.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.emmpann.aplikasistoryapp.R
import com.github.emmpann.aplikasistoryapp.core.component.StoryAdapter
import com.github.emmpann.aplikasistoryapp.core.data.factory.ViewModelFactoryStory
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.ListStoryItem
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.Result
import com.github.emmpann.aplikasistoryapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var storyAdapter: StoryAdapter

    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactoryStory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction(view)
        setupItem()
    }

    private fun setupAction(view: View) {
        binding.addStoryButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    // logout
                    viewModel.logout()
                    view.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                    true
                }

                else -> {true}
            }
        }
    }

    private fun setupItem() {
        binding.rvStory.apply {
            storyAdapter = StoryAdapter()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = storyAdapter
            storyAdapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ListStoryItem) {
                    val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                    toDetailFragment.storyId = data.id
                    findNavController().navigate(toDetailFragment)
                }
            })
        }

        viewModel.getAllStory().observe(viewLifecycleOwner) {result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                    storyAdapter.clearList()
                }

                is Result.Success -> {
                    showLoading(false)
                    storyAdapter.setList(result.data)
                }

                is Result.Error -> {
                    showLoading(false)
                    showToast(result.error)
                }

                else ->{}
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}