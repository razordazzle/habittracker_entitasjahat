package com.example.habittracker_entitasjahat.model

data class Habit(
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