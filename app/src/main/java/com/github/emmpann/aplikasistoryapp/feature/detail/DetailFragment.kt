package com.github.emmpann.aplikasistoryapp.feature.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.ResultApi
import com.github.emmpann.aplikasistoryapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolBar()
        setupData()
    }

    private fun setupToolBar() {
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.title = null
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupData() {
        val storyId = DetailFragmentArgs.fromBundle(arguments as Bundle).storyId

        viewModel.getStoryDetail(storyId).observe(viewLifecycleOwner) { result ->
            when(result) {
                is ResultApi.Loading -> {
                    showLoading(true)
                }

                is ResultApi.Success -> {
                    showLoading(false)
                    with(binding) {
                        Glide.with(requireContext()).load(result.data.photoUrl).into(imageView)
                        titleStory.text = result.data.name
                        descStory.text = result.data.description
                    }
                }

                is ResultApi.Error -> {
                    showLoading(false)
                    showToast(result.error)
                }
            }

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}