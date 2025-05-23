package com.example.notes.di

import com.example.notes.AuthIntercepter
import com.example.notes.api.NotesAPi
import com.example.notes.api.UserAPI
import com.example.notes.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofitBuilder():Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)

    }
    @Singleton
    @Provides
    fun provideOKHttpClient(interceptor: AuthIntercepter): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun provideUserAPI(retrofitBuilder: Retrofit.Builder):UserAPI{
        return  retrofitBuilder.build().create(UserAPI::class.java)
    }
    fun provideNoteApi(retrofitBuilder: Retrofit.Builder,OkHttpClient: OkHttpClient): NotesAPi{
        return retrofitBuilder
            .client(OkHttpClient)
            .build().create(NotesAPi::class.java)

    }

}