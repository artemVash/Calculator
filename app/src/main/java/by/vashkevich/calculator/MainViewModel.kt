package by.vashkevich.calculator

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {

    private var _textCalculation  = MutableLiveData<String>()
    val textCalculation:LiveData<String> = _textCalculation

    fun setTextCalculation(sum:String){
        _textCalculation.value = sum
    }

}