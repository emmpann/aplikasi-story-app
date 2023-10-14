package com.github.emmpann.aplikasistoryapp.core.data.remote.retrofit

import android.util.Log
import com.github.emmpann.aplikasistoryapp.core.data.local.pref.UserPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userPreference: UserPreference,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            userPreference.getSession().first().token
        }

        Log.d("AuthInterceptor", "token: $token")
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}