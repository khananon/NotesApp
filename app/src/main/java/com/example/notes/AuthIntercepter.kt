package com.example.notes

import com.example.notes.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

@Inject
lateinit var  tokenManager: TokenManager
class AuthIntercepter  @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
      val request =chain.request().newBuilder()
        val token= tokenManager.getToken()
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}