package com.github.emmpann.aplikasistoryapp.feature.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.github.emmpann.aplikasistoryapp.R
import com.github.emmpann.aplikasistoryapp.core.data.factory.ViewModelFactoryStory
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.Result
import com.github.emmpann.aplikasistoryapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactoryStory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
    }

    private fun setupData() {
        val storyId = DetailFragmentArgs.fromBundle(arguments as Bundle).storyId

        Log.d("DetailFragment", storyId)

        viewModel.getStoryDetail(storyId).observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    with(binding) {
                        Glide.with(requireContext()).load(result.data.photoUrl).into(imageView)
                        titleStory.text = result.data.name
                        descStory.text = result.data.description
                    }
                }

                is Result.Error -> {
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