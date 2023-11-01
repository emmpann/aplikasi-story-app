package com.github.emmpann.aplikasistoryapp.feature.add

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.github.emmpann.aplikasistoryapp.R
import com.github.emmpann.aplikasistoryapp.core.component.getImageUri
import com.github.emmpann.aplikasistoryapp.core.component.reduceFileImage
import com.github.emmpann.aplikasistoryapp.core.component.uriToFile
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.ResultApi
import com.github.emmpann.aplikasistoryapp.databinding.FragmentAddBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding

    @Inject
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel: AddViewModel by viewModels()

    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPermission()
        setupToolbar()
        setupAction()
        uploadImageObserve()
    }

    private fun uploadImageObserve() {
        viewModel.addStoryResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultApi.Loading -> {
                    showLoading(true)
                }

                is ResultApi.Success -> {
                    showLoading(false)
                    showToast(result.data.message)
                    view?.findNavController()
                        ?.navigate(R.id.action_addFragment_to_homeFragment)
                }

                is ResultApi.Error -> {
                    showLoading(false)
                    showToast(result.error)
                }

                else -> {}
            }
        }
    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.title = null
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

    }

    private fun setupAction() {
        with(binding) {
            cameraButton.setOnClickListener {
                startCamera()
            }

            galleryButton.setOnClickListener {
                startGallery()
            }

            submitButton.setOnClickListener {
                uploadImage()
            }
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.imagePreview.setImageURI(it)
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->

            val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
            val description = binding.descEditText.text.toString()

            if (binding.locationSwitch.isChecked) {
                getCurrentLocation {
                    viewModel.uploadStory(imageFile, description, it[0], it[1])
                    Log.d("AddFragment", "lat: ${it[0]}, lon: ${it[1]}")
                }
            } else {
                viewModel.uploadStory(imageFile, description, 0f, 0f)
            }

        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri)
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                }

                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                }

                else -> {}
            }
        }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getCurrentLocation(callback: (ArrayList<Float>) -> Unit) {
        // [lat, lon]

        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->

                if (location != null) {
                    callback(arrayListOf(location.latitude.toFloat(), location.longitude.toFloat()))
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.location_is_not_found_try_again),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}