package com.jacs.cordova.fakeusercomposer.repository

import androidx.lifecycle.LiveData
import com.jacs.cordova.fakeusercomposer.datasource.RestDataSource
import com.jacs.cordova.fakeusercomposer.model.User
import com.jacs.cordova.fakeusercomposer.model.UserDao
import kotlinx.coroutines.delay
import javax.inject.Inject

interface UserRepository {
    suspend fun getNewUser(): User
    suspend fun deleteUser(user: User)
    fun getAllUsers(): LiveData<List<User>>
}

class UserRepositoryImpl @Inject constructor(
    private val restDataSource: RestDataSource,
    private val userDao: UserDao
): UserRepository {

    override suspend fun getNewUser():User {
        //delay(2000)
        val name = restDataSource.getUserNames().results?.get(0)?.name!!
        val location = restDataSource.getUserLocation().results?.get(0)?.location!!
        val picture = restDataSource.getUserPicture().results?.get(0)?.picture!!

        val user =  User(
            name = name.first,
            lastName = name.last,
            city = location.city,
            thumbnail = picture.thumbnail
        )
        userDao.insertUser(user)
        return user
    }

    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    override fun getAllUsers(): LiveData<List<User>> {
        return userDao.getAllUsers()
    }

}