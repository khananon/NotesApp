package com.example.notes.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.api.UserAPI
import com.example.notes.models.UserRequest
import com.example.notes.models.UserResponse
import com.example.notes.utils.Constants.TAG
import com.example.notes.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject


class UserRepository @Inject constructor(private val userApi: UserAPI) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = _userResponseLiveData

    suspend fun registerUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userApi.signup(userRequest)
        handleResonse(response)
    }


    suspend fun loginUser(userRequest: UserRequest) {
        val response = userApi.signin(userRequest)
        handleResonse(response)
    }

    private fun handleResonse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue(NetworkResult.Error(errObj.getString("message")))

        } else {
            _userResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

}