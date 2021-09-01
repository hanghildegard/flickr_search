package com.tori.flickrsearch.network

import com.tori.flickrsearch.data.services.PhotoSearchApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    companion object {
        const val BASE_URL = "https://www.flickr.com"

        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build()
        }

        fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
            return OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()
        }

        fun providePhotoSearchApi(retrofit: Retrofit): PhotoSearchApi =
            retrofit.create(PhotoSearchApi::class.java)
    }
}