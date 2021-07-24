package by.vashkevich.calculator.dataBase.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import by.vashkevich.calculator.dataBase.entity.DataRequest

interface DataRequestDao {

    @Insert
    suspend fun insert(data:DataRequest)

    @Delete
    suspend fun delete(data:DataRequest)

    @Update
    suspend fun upDate(data:DataRequest)

    @Query("SELECT * FROM data_request_table")
    suspend fun getAll() : List<DataRequest>

    @Query("DELETE FROM data_request_table")
    suspend fun clearAll()
}