package com.example.flightsearchapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FlightSearchDao {
    @Query(
        """SELECT * FROM airport 
           WHERE iata_code LIKE '%' || :searchQuery || '%' or name LIKE '%' || :searchQuery || '%' """
    )
    fun searchAirport(searchQuery: String): Flow<List<Airport>>
    @Query(
        """SELECT * FROM airport 
           WHERE iata_code = :iataCode LIMIT 1"""
    )
    fun getAirportByIataCode(iataCode: String): Flow<Airport?>

    @Query(
        """SELECT * FROM airport 
           ORDER BY passengers ASC"""
    )
    fun getAllAirports(): Flow<List<Airport>>
    @Query(
        "SELECT * FROM favorite "
    )
    fun getFavoriteRoutes(): Flow<List<FavoriteRoute>>

    @Query(
        """SELECT * FROM favorite 
           WHERE departure_code = :deapartureIataCode AND destination_code = :arrivalIataCode LIMIT 1"""
    )
    fun getFavoriteRouteByIataCodes(deapartureIataCode: String, arrivalIataCode: String): Flow<FavoriteRoute?>


    @Query(
        """SELECT * FROM favorite WHERE departure_code = :departureCode AND destination_code = :destinationCode LIMIT 1 """
    )
    fun getFavoriteRouteByCodes(departureCode: String, destinationCode: String): Flow<FavoriteRoute?>

    @Query(
        """DELETE FROM favorite WHERE departure_code = :departureCode AND destination_code = :destinationCode"""
    )
    suspend fun deleteFavoriteRoute(departureCode: String, destinationCode: String)

    @Insert
    suspend fun insertFavoriteRoute(favoriteRoute: FavoriteRoute)
    @Insert
    suspend fun insertAirport(airport: Airport) // For testing purposes

}