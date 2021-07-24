package by.vashkevich.calculator.dataBase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_request_table")
data class DataRequest(
    @ColumnInfo(name = "rate")
    val rate:String,
    @ColumnInfo(name = "initial_capital")
    val initialCapital:String,
    @ColumnInfo(name = "final_capital")
    val finalCapital:String,
    @ColumnInfo(name = "type_of_capitalization")
    val typeOfCapitalization:String,
    @ColumnInfo(name = "replenishment_in_A_month")
    val replenishmentInAMonth:String,
    @ColumnInfo(name = "result")
    val result:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null
){}
