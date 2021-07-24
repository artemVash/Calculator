package by.vashkevich.calculator.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.vashkevich.calculator.dataBase.dao.DataRequestDao
import by.vashkevich.calculator.dataBase.entity.DataRequest

@Database(entities =[DataRequest::class], version = 1)
abstract class MyDataBase : RoomDatabase() {

    abstract fun dataRequestDao():DataRequestDao

    var INSTANCE:MyDataBase? = null

    fun getDataBase(context: Context):MyDataBase{

        return if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context,MyDataBase::class.java,"database_data_request").build()
            INSTANCE as MyDataBase
        }else INSTANCE as MyDataBase
    }
}