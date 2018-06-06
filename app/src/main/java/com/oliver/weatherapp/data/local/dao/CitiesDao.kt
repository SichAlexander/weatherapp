package com.oliver.weatherapp.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.oliver.weatherapp.data.local.model.CityEntry
import io.reactivex.Flowable

@Dao
interface CitiesDao {

    @Query("SELECT * FROM cities ORDER BY id DESC")
    fun getAll(): Flowable<List<CityEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: CityEntry) : Long

    @Query("DELETE FROM cities WHERE id = :id")
    fun deleteCity(id: Long)

}
