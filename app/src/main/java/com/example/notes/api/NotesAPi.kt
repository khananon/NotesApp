package com.example.notes.api

import com.example.notes.models.NotesRequest
import com.example.notes.models.NotesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotesAPi {

    @GET("/notes")
    suspend fun getNotes(): Response<List<NotesResponse>>
    @POST("/notes")
    suspend fun  createNotes(@Body notesRequest: NotesRequest): Response<NotesResponse>
    @PUT("/notes/{noteID}")
    suspend fun updateNotes(@Path("noteID") noteID: String,@Body notesRequest: NotesRequest): Response<NotesResponse>

    @DELETE("/notes/{noteID}")
    suspend fun deleteNotes(@Path("noteID") noteID: String): Response<NotesResponse>

}