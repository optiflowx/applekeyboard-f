package com.optiflowx.applekeyboard.database.repository

import androidx.lifecycle.LiveData
import com.optiflowx.applekeyboard.database.dao.AppDatabaseDAO
import com.optiflowx.applekeyboard.database.entities.AppData

class AppDataRepository(private val appDataDAO: AppDatabaseDAO) {
    val readAllData : LiveData<List<AppData>> =  appDataDAO.getAll()
    suspend fun addTodo(appData: AppData) {
        appDataDAO.insert(appData)
    }
    suspend fun updateTodo(appData: AppData) {
        appDataDAO.update(appData)
    }
}