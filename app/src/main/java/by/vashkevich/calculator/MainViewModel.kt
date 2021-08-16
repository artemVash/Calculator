package by.vashkevich.calculator

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.vashkevich.calculator.dataBase.MyDataBase
import by.vashkevich.calculator.dataBase.entity.DataRequest
import by.vashkevich.calculator.dataBase.repository.DataRequestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val dataRequestRepository = DataRequestRepository(MyDataBase.getDataBase(application))
    private val ioScope = CoroutineScope((Dispatchers.IO))

    private var _textCalculation  = MutableLiveData<String>()
    val textCalculation:LiveData<String> = _textCalculation

    fun clearTextCalculation(){
        _textCalculation.value = ""
    }

    private var _allDataRequest = MutableLiveData<List<DataRequest>>()
    val allDataRequest:LiveData<List<DataRequest>> = _allDataRequest

    fun setTextCalculation(sum:String){
        _textCalculation.value = sum
    }

    fun insert(data:DataRequest){
        dataRequestRepository.insert(data)
    }

    fun delete(data:DataRequest){
        dataRequestRepository.delete(data)
    }

    fun upData(data:DataRequest){
        dataRequestRepository.upData(data)
    }

    fun clearAll(){
        dataRequestRepository.clearAll()
    }

    fun getAll() {
        ioScope.launch {
            val dataList = dataRequestRepository.getAll()
            _allDataRequest.postValue(dataList)
        }

    }



}