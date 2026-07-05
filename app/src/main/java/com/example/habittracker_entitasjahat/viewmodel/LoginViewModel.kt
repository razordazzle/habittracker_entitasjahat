package com.example.habittracker_entitasjahat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.habittracker_entitasjahat.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getDatabase(application).userDao()

    val loginResult = MutableLiveData<Boolean?>()

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userDao.login(username, password)
            loginResult.postValue(user != null)
        }
    }

    fun resetLoginResult() {
        loginResult.value = null
    }
}