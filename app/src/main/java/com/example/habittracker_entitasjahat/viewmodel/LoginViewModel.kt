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
class LoginViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {


    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
    val loginResult = MutableLiveData<Boolean?>()

    fun login(username: String, password: String) {
        launch {
            val db = AppDatabase.getDatabase(getApplication())
            val user = db.userDao().login(username, password)

            if (user != null) {
                loginResult.postValue(true)
            } else {
                loginResult.postValue(false)
            }
        }
    }
}