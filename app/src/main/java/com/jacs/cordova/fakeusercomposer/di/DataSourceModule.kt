package com.jacs.cordova.fakeusercomposer.di

import android.content.Context
import androidx.room.Room
import com.jacs.cordova.fakeusercomposer.datasource.DatabaseDataSource
import com.jacs.cordova.fakeusercomposer.datasource.RestDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    // api rest
    @Singleton
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl(): String {
        return "https://randomuser.me/api/"
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        @Named("BaseUrl") baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Singleton
    @Provides
    fun restDataSource(retrofit: Retrofit): RestDataSource {
        return retrofit.create(RestDataSource::class.java)
    }

    // database
    @Singleton
    @Provides
    fun dbDataSource(@ApplicationContext context: Context): DatabaseDataSource{
        return Room.databaseBuilder(
                context,
                DatabaseDataSource::class.java,
                "user_database_room"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun userDao(db: DatabaseDataSource) = db.userDao();

}