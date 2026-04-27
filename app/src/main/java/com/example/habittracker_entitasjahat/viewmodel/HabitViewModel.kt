package com.example.habittracker_entitasjahat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.habittracker_entitasjahat.model.Habit
import com.example.habittracker_entitasjahat.util.FileHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HabitViewModel(application: Application) : AndroidViewModel(application) {
    val habitsLD = MutableLiveData<ArrayList<Habit>>()

    private val fileHelper = FileHelper(getApplication())

    fun loadHabits() {
        val jsonString = fileHelper.readFromFile()

        if (jsonString.isEmpty()) {
            habitsLD.value = arrayListOf()
        } else {
            val sType = object : TypeToken<ArrayList<Habit>>() {}.type
            val result = Gson().fromJson<ArrayList<Habit>>(jsonString, sType)
            habitsLD.value = result
        }
    }

    private fun saveHabits(habitList: ArrayList<Habit>) {
        val jsonString = Gson().toJson(habitList)
        fileHelper.writeToFile(jsonString)
    }

    fun addHabit(name: String, description: String, goal: Int, unit: String, iconName: String) {
        val currentList = habitsLD.value ?: arrayListOf()

        val newHabit = Habit(
            name = name,
            description = description,
            goal = goal,
            unit = unit,
            progress = 0,
            iconName = iconName
        )

        currentList.add(newHabit)
        habitsLD.value = currentList
        saveHabits(currentList)
    }

    fun increaseProgress(position: Int) {
        val currentList = habitsLD.value ?: arrayListOf()

        if (position >= 0 && position < currentList.size) {
            val habit = currentList[position]

            if (habit.progress < habit.goal) {
                habit.progress++
            }

            habitsLD.value = currentList
            saveHabits(currentList)
        }
    }

    fun decreaseProgress(position: Int) {
        val currentList = habitsLD.value ?: arrayListOf()

        if (position >= 0 && position < currentList.size) {
            val habit = currentList[position]

            if (habit.progress > 0) {
                habit.progress--
            }

            habitsLD.value = currentList
            saveHabits(currentList)
        }
    }
}