package com.example.notes.repository

import android.hardware.biometrics.BiometricManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.api.NotesAPi
import com.example.notes.models.NotesRequest
import com.example.notes.models.NotesResponse
import com.example.notes.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NotesRepository @Inject constructor(private val notesApi: NotesAPi) {
    private val _notesLiveData = MutableLiveData<NetworkResult<List<NotesResponse>>>()
    val notesLiveData : LiveData<NetworkResult<List<NotesResponse>>>
    get() = _notesLiveData
    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData : LiveData<NetworkResult<String>>
    get() = _statusLiveData

    suspend fun getNotes(){
        _notesLiveData.postValue(NetworkResult.Loading())
        val response = notesApi.getNotes()
        if (response.isSuccessful && response.body() != null) {
            _notesLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _notesLiveData.postValue(NetworkResult.Error(errObj.getString("message")))

        } else {
            _notesLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
    suspend fun createNotes(notesRequest: NotesRequest) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response= notesApi.createNotes(notesRequest )
        HandleResponse(response,"Notes Created")

    }

    private fun HandleResponse(response: Response<NotesResponse>,message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(message))

        } else {
            _statusLiveData.postValue(NetworkResult.Error(message))
        }
    }

    suspend fun  deleteNotes(notesId: String){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesApi.deleteNotes(notesId )
        HandleResponse(response,"Notes Deleted")

    }
    suspend fun updateNotes(notesRequest: NotesRequest,notesId: String){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response=notesApi.updateNotes(notesId,notesRequest)
        HandleResponse(response, "Notes Updated")
    }
    }

