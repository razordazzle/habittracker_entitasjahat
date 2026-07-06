package com.example.habittracker_entitasjahat.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.habittracker_entitasjahat.model.Habit

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getAllHabits():LiveData<List<Habit>>

    @Insert
    fun insertHabit(habit:Habit)

    @Update
    fun updateHabit(habit:Habit)

    @Delete
    fun deleteHabit(habit:Habit)
}