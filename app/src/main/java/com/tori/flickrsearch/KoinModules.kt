package com.tori.flickrsearch

import com.tori.flickrsearch.data.services.PhotoSearchService
import com.tori.flickrsearch.data.services.PhotoSearchServiceHandler
import com.tori.flickrsearch.network.AuthInterceptor
import com.tori.flickrsearch.network.RetrofitFactory
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