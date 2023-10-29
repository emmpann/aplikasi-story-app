package com.github.emmpann.aplikasistoryapp.feature.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.github.emmpann.aplikasistoryapp.R
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.ResultApi
import com.github.emmpann.aplikasistoryapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playAnimation()
        setupAction()
        loginResponseObserve()
    }

    private fun loginResponseObserve() {

        viewModel.loginResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultApi.Loading -> {
                    // show loading
                    showLoading(true)
                }

                is ResultApi.Success -> {
                    showLoading(false)
                    // name, userid, token
                    viewModel.saveSession(result.data.loginResult)
                    showToast(result.data.message)
                }

                is ResultApi.Error -> {
                    showLoading(false)
                    showDialog(result.error)
                }
            }
        }
    }

    private fun setupAction() {
        with(binding) {
            loginButton.setOnClickListener {
                viewModel.login(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditText =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditText =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val loginButton =
            ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                emailTextView,
                emailEditText,
                passwordTextView,
                passwordEditText,
                loginButton
            )
            start()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.error))
            setMessage(message)
            setPositiveButton(getString(R.string.yes)) { _, _ ->

            }
            create()
            show()
        }
    }
}