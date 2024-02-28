package com.jacs.cordova.fakeusercomposer.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jacs.cordova.fakeusercomposer.model.User
import com.jacs.cordova.fakeusercomposer.model.UserDao

@Database(entities = [User::class], version = 1)
abstract  class DatabaseDataSource : RoomDatabase(){

    abstract fun userDao(): UserDao

}