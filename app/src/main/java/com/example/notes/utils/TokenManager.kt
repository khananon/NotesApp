package com.example.notes.utils

import android.content.Context
import com.example.notes.utils.Constants.PREF_TOKEN_FILE
import com.example.notes.utils.Constants.USER_TOKEN
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor( @ApplicationContext context: Context) {
    private val prerf= context.getSharedPreferences(PREF_TOKEN_FILE,Context.MODE_PRIVATE)//we use context.MODE_PRIVATE  so it dont share with anyone

    fun saveToken(token:String){
        val editor=prerf.edit()
        editor.putString(USER_TOKEN,token)
        editor.apply()
    }
    fun getToken(): String? {
        return prerf.getString(USER_TOKEN,null)
    }
}