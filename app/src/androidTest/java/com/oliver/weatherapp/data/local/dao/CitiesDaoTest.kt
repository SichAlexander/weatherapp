package com.oliver.weatherapp.data.local.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.oliver.weatherapp.data.local.WeatherDatabase
import com.oliver.weatherapp.data.local.model.CityEntry
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CitiesDaoTest {

    @Rule
    @JvmField
    public val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var citiesDao: CitiesDao
    private lateinit var db: WeatherDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, WeatherDatabase::class.java)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build()

        citiesDao = db.citiesDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadCitiesList_Matches() {
        val city = CityEntry("city1", "address1", 0.0, 0.0)

        val inserted = citiesDao.insert(city)

        citiesDao.getAll()
                .test()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValue {
                    it.size.toLong() == inserted
                }
                .assertValue { areTheSame(it[0], city) }
                .assertSubscribed()
    }

    @Test
    fun readEmptyCitiesList_EmptyList() {

        citiesDao.getAll()
                .test()
                .assertValue { it.isEmpty() }
                .assertSubscribed()
    }

    @Test
    fun writeReadDifferentCities_returnCombination() {

        val city1 = CityEntry("city1", "address1", 0.0, 0.0)
        citiesDao.insert(city1)

        val city2 = CityEntry("city2", "address2", 2.0, 2.0)
        citiesDao.insert(city2)

        val resultCities = listOf(city1, city2)

        citiesDao.getAll()
                .test()
                .assertValueCount(1)
                .assertValue {
                    it.size == resultCities.size
                }
                .assertSubscribed()
    }


    private fun areTheSame(first: CityEntry?, second: CityEntry?): Boolean {
        return first?.name == second?.name &&
                first?.address == second?.address &&
                first?.latitude == second?.latitude &&
                first?.longitude == second?.longitude
    }
}