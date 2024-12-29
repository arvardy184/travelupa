package com.application.travelupa.database


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Insert
    fun insert(image: ImageEntity)

    @Query("SELECT * FROM images WHERE id=:imageId")
    fun getImageById(imageId: String): ImageEntity?

    @Query("SELECT * FROM images WHERE tempatWisataId=:firestoreId")
    fun getImageByTempatWisataId(firestoreId: String): ImageEntity?

    @Query("SELECT * FROM images")
    fun getAllImages(): Flow<List<ImageEntity>>

    @Delete
    fun delete(image: ImageEntity)
}