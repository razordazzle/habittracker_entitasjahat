package com.example.habittracker_entitasjahat.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="habits")
data class Habit(
    @PrimaryKey(autoGenerate=true)
    var id:Int=0,
    var name: String?,
    var description: String?,
    var goal: Int,
    var unit: String?,
    var progress: Int,
    var iconName: String?
) {
    fun isCompleted(): Boolean {
        return progress >= goal
    }
}