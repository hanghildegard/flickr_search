package com.tori.flickrsearch

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.tori.flickrsearch.data.services.PhotoSearchService
import com.tori.flickrsearch.data.services.PhotoSearchServiceHandler
import com.tori.flickrsearch.network.AuthInterceptor
import com.tori.flickrsearch.network.RetrofitFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {
    factory { AuthInterceptor() }
    factory { RetrofitFactory.provideOkHttpClient(get()) }
    factory { RetrofitFactory.providePhotoSearchApi(get()) }
    single { RetrofitFactory.provideRetrofit(get()) }
}

val photoSearchModule = module {
    single<PhotoSearchService> { PhotoSearchServiceHandler(get()) }
}

val picassoModule = module {
    single {
        val downloader = okHttp3Downloader(get())
        picasso(androidContext(), downloader)
    }
}


private fun okHttp3Downloader(client: OkHttpClient) = OkHttp3Downloader(client)
private fun picasso(context: Context, downloader: OkHttp3Downloader) =
    Picasso.Builder(context)
        .downloader(downloader)
        .build()