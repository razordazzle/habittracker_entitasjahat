package com.example.habittracker_entitasjahat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.habittracker_entitasjahat.model.Habit
import com.example.habittracker_entitasjahat.database.AppDatabase
import com.example.habittracker_entitasjahat.database.HabitDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitViewModel(application: Application) : AndroidViewModel(application) {
    private val db=AppDatabase.getDatabase(getApplication())
    private val habitDao:HabitDao=db.habitDao()
    val habitsLD=habitDao.getAllHabits()

    fun loadHabits(){}

    fun addHabit(name: String, description: String, goal: Int, unit: String, iconName: String) {
        val newHabit=Habit(name=name,description=description,goal=goal,unit=unit,progress=0,iconName=iconName)
        CoroutineScope(Dispatchers.IO).launch{habitDao.insertHabit(newHabit)}
    }

    fun increaseProgress(habit: Habit) {
        if(habit.progress<habit.goal){
            habit.progress++
            CoroutineScope(Dispatchers.IO).launch{habitDao.updateHabit(habit)}
        }
    }

    fun decreaseProgress(habit: Habit) {
        if(habit.progress>0){
            habit.progress--
            CoroutineScope(Dispatchers.IO).launch{habitDao.updateHabit(habit)}
        }
    }
}