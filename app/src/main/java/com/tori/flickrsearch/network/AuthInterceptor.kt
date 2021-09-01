package com.tori.flickrsearch.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        val url = req.url.newBuilder().addQueryParameter("api_key", "b59eaa142fbb03d0ba6c93882fd62e30").build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}