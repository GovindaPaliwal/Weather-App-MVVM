package com.gpfreetech.weather.ui.main.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gpfreetech.weather.ui.main.data.model.WeatherDetail

@Dao
interface WeatherDetailDao {

    /**
     * Duplicate values are replaced in the table.
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWeather(weatherDetail: WeatherDetail)

    @Query("SELECT * FROM ${WeatherDetail.TABLE_NAME} WHERE cityName = :cityName")
    fun fetchWeatherByCity(cityName: String): WeatherDetail?

    @Query("SELECT * FROM ${WeatherDetail.TABLE_NAME}")
    fun fetchSearchCity(): LiveData<List<WeatherDetail>>

}
