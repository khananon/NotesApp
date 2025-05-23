package com.example.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.models.NotesRequest
import com.example.notes.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val notesRepository: NotesRepository) : ViewModel() {

    val notesLiveData get() = notesRepository.notesLiveData
    val statusLiveData get() = notesRepository.statusLiveData

       fun getNotes() {

           viewModelScope.launch {
               notesRepository.getNotes()
           }

       }
    fun createNotes(notesRequest: NotesRequest) {
        viewModelScope.launch {
            notesRepository.createNotes(notesRequest)
        }
    }
    fun updateNotes(notesRequest: NotesRequest,notesId: String) {
        viewModelScope.launch {
            notesRepository.updateNotes(notesRequest,notesId)
        }
    }
    fun deleteNotes(notesId: String) {
        viewModelScope.launch {
            notesRepository.deleteNotes(notesId)
        }
    }


}