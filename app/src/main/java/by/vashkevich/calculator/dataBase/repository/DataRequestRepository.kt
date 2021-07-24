package by.vashkevich.calculator.dataBase.repository

import by.vashkevich.calculator.dataBase.MyDataBase
import by.vashkevich.calculator.dataBase.entity.DataRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DataRequestRepository(
    database:MyDataBase
) {
    private val dao = database.dataRequestDao()
    private val ioScope = CoroutineScope(Dispatchers.IO)

    fun insert(data:DataRequest){
        ioScope.launch {
            dao.insert(data)
        }
    }

    fun delete(data:DataRequest){
        ioScope.launch {
            dao.delete(data)
        }
    }

    fun upData(data:DataRequest){
        ioScope.launch {
            dao.upDate(data)
        }
    }

    suspend fun getAll() : List<DataRequest>{
        return ioScope.async {
            dao.getAll()
        }.await()
    }

    fun clearAll(){
        ioScope.launch {
            dao.clearAll()
        }
    }

}