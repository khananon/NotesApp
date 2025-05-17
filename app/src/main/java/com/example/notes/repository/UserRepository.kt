package com.example.notes.repository

import android.util.Log
import com.example.notes.api.UserAPI
import com.example.notes.models.UserRequest
import com.example.notes.utils.Constants.TAG

class UserRepository constructor(private val userApi: UserAPI) {
    suspend fun  registerUser(userRequest: UserRequest){
        val response=userApi.signup(userRequest)
        Log.d(TAG,response.body().toString())
    }
    suspend fun  loginUser(userRequest: UserRequest){
        val response=userApi.signin(userRequest)
        Log.d(TAG,response.body().toString())
    }
}