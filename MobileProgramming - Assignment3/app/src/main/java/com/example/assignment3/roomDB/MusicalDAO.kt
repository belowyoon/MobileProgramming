package com.example.assignment3.roomDB
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MusicalDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMusical(musical: Musical)

    @Query("Select * from musicals")
    fun getAllMusical(): List<Musical>

    @Query("Select COUNT(*) from musicals")
    fun getCount() :Int

    @Query("Select * from musicals where mName like :name")
    fun findMusical(name: String): List<Musical>

    @Query("Select * from musicals where place like :place")
    fun findMusicalByPlace(place: String): List<Musical>

    @Query("Delete from musicals")
    fun deleteMusical()

}
