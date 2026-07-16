package com.example.habittracker_entitasjahat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.habittracker_entitasjahat.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
class LoginViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope {

    private val userDao = AppDatabase.getDatabase(application).userDao()
    val loginResult = MutableLiveData<Boolean?>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun login(username: String, password: String) {
        launch {
            val user = userDao.login(username, password)
            loginResult.postValue(user != null)
        }
    }
}