package com.example.habittracker_entitasjahat.util

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileHelper(val context: Context) {
    private val folderName = "habittracker"
    private val fileName = "habits.txt"

    private fun getFile(): File {
        val dir = File(context.filesDir, folderName)

        if (!dir.exists()) {
            dir.mkdirs()
        }

        return File(dir, fileName)
    }

    fun writeToFile(data: String) {
        try {
            val file = getFile()
            FileOutputStream(file, false).use { output ->
                output.write(data.toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun readFromFile(): String {
        return try {
            val file = getFile()

            if (!file.exists()) {
                ""
            } else {
                file.bufferedReader().useLines { lines ->
                    lines.joinToString("\n")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }
}