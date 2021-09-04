package by.vashkevich.calculator

import android.app.Application
import android.util.Log
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.vashkevich.calculator.dataBase.MyDataBase
import by.vashkevich.calculator.dataBase.entity.DataRequest
import by.vashkevich.calculator.helpers.InputDataState
import by.vashkevich.calculator.dataBase.repository.DataRequestRepository
import by.vashkevich.calculator.extensions.default
import by.vashkevich.calculator.extensions.set
import by.vashkevich.calculator.repository.PaymentRepositoryImp
import kotlinx.coroutines.*
import java.text.DecimalFormat

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dataRequestRepository = DataRequestRepository(MyDataBase.getDataBase(application))
    private val ioScope = CoroutineScope((Dispatchers.IO))
    private val paymentRepositoryImp = PaymentRepositoryImp()

    private val decimalFormat = DecimalFormat("#.#")
    private val decimalFormatWeek = DecimalFormat("#")

    private var _textCalculation = MutableLiveData<String>()
    val textCalculation: LiveData<String> = _textCalculation

    fun clearTextCalculation() {
        _textCalculation.value = ""
    }

    //Database
    private var _allDataRequest = MutableLiveData<List<DataRequest>>()
    val allDataRequest: LiveData<List<DataRequest>> = _allDataRequest

    fun insert(data: DataRequest) {
        dataRequestRepository.insert(data)
    }

    fun delete(data: DataRequest) {
        dataRequestRepository.delete(data)
    }

    fun upData(data: DataRequest) {
        dataRequestRepository.upData(data)
    }

    fun clearAll() {
        dataRequestRepository.clearAll()
    }

    fun getAll() {
        ioScope.launch {
            val dataList = dataRequestRepository.getAll()
            _allDataRequest.postValue(dataList)
        }

    }

    //data validation logic

    val state =
        MutableLiveData<InputDataState>().default(initialValue = InputDataState.DefaultState())

    fun dataReception(
        initialCapital: String,
        interestRate: String,
        finalCapital: String,
        firstRadioButton: RadioButton,
        secondRadioButton: RadioButton,
        checkBoxButton: CheckBox,
        monthReplenishment: String
    ) {
        if (initialCapitalValidate(initialCapital = initialCapital)) {
            state.set(newValue = InputDataState.ErrorTextFieldsState(message = getSomeString(R.string.toast_fill_all_fields)))
            return
        }
        if (interestRateValidate(interestRate = interestRate)) {
            state.set(newValue = InputDataState.ErrorTextFieldsState(message = getSomeString(R.string.toast_fill_all_fields)))
            return
        }

        if (finalCapitalValidate(finalCapital = finalCapital)) {
            state.set(newValue = InputDataState.ErrorTextFieldsState(message = getSomeString(R.string.toast_fill_all_fields)))
            return
        }

        if (!checkedRadioButton(btnFirst = firstRadioButton, btnSecond = secondRadioButton)) {
            state.set(newValue = InputDataState.ErrorRadioButtonState(message = getSomeString(R.string.toast_choose_type)))
            return
        }

        if (checkedCheckBox(checkBox = checkBoxButton, monthReplenishment = monthReplenishment)) {
            state.set(
                newValue = InputDataState.ErrorCheckBoxReplenishmentState(
                    message = getSomeString(
                        R.string.toast_input_sum_month_replenishment
                    )
                )
            )
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val week = paymentRepositoryImp.payment(
                initialCapital,
                interestRate,
                finalCapital,
                firstRadioButton,
                secondRadioButton,
                checkBoxButton,
                monthReplenishment
            )

            var result = ""

            if (firstRadioButton.isChecked) {
                result = finalDateMonth(week)
                withContext(Dispatchers.Main) {
                    _textCalculation.set(finalDateMonth(week))
                }
            } else if (secondRadioButton.isChecked) {
                result = finalDateWeek(week)
                withContext(Dispatchers.Main) {
                    _textCalculation.set(finalDateWeek(week))
                }
            }
            insert(
                addDataRequest(
                    rate = interestRate,
                    initial = initialCapital,
                    final = finalCapital,
                    firstRadioButton = firstRadioButton,
                    checkBoxButton = checkBoxButton,
                    replenishment = monthReplenishment,
                    result = result
                )
            )
        }
    }

    // Internal logic
    private fun initialCapitalValidate(initialCapital: String): Boolean {
        return initialCapital.isEmpty()
    }

    private fun interestRateValidate(interestRate: String): Boolean {
        return interestRate.isEmpty()
    }

    private fun finalCapitalValidate(finalCapital: String): Boolean {
        return finalCapital.isEmpty()
    }

    private fun checkedRadioButton(btnFirst: RadioButton, btnSecond: RadioButton): Boolean {
        return btnFirst.isChecked || btnSecond.isChecked
    }

    private fun checkedCheckBox(checkBox: CheckBox, monthReplenishment: String): Boolean {
        return checkBox.isChecked && monthReplenishment.isEmpty()
    }

    private fun finalDateMonth(weeks: Double): String {

        var month = weeks / 4.3
        var counterYear = 0

        if (month < 12) {
            return textMonth(decimalFormat.format(month))
        } else {
            while (month > 12) {
                month -= 12
                counterYear++
            }
            Log.e("month", decimalFormat.format(month))
            return "${textYear(counterYear)} ${textMonth(decimalFormat.format(month))}"
        }
    }

    private fun finalDateWeek(weeks: Double): String {

        var remainderWeeks = weeks
        var counterMonth = 0

        return if (remainderWeeks < 4.3) {
            textWeek(decimalFormat.format(remainderWeeks))
        } else {
            while (remainderWeeks > 4.3) {
                remainderWeeks -= 4.3
                counterMonth++
            }

            if (counterMonth == 0) {
                textWeek(decimalFormat.format(remainderWeeks))
            } else {
                Log.e("counterMonth", counterMonth.toString())
                "${finalDateMonth(counterMonth * 4.3)} ${
                    textWeek(
                        decimalFormatWeek.format(
                            remainderWeeks
                        )
                    )
                }"
            }
        }
    }

    private fun textYear(x: Int): String {

        val a = x % 10
        val b = x % 100

        return if (a == 2 && b != 12 || a == 3 && b != 13 || a == 4 && b != 14) {
            "$x ${getSomeString(R.string.years)}"
        } else if (a == 1 && b != 11) {
            "$x ${getSomeString(R.string.year)}"
        } else {
            "$x ${getSomeString(R.string.years2)}"
        }
    }

    private fun textMonth(x: String): String {
        val toInt = x.toInt()

        val a = toInt % 10
        val b = toInt % 100

        return if (a == 2 && b != 12 || a == 3 && b != 13 || a == 4 && b != 14) {
            "$x ${getSomeString(R.string.months_result)}"
        } else if (a == 1 && b != 11) {
            "$x ${getSomeString(R.string.month_result)}"
        } else {
            "$x ${getSomeString(R.string.months2_result)}"
        }
    }

    private fun textWeek(x: String): String {
        val toInt = x.toInt()

        val a = toInt % 10
        val b = toInt % 100

        return if (a == 2 && b != 12 || a == 3 && b != 13 || a == 4 && b != 14) {
            "$x ${getSomeString(R.string.weeks_result)}"
        } else if (a == 1 && b != 11) {
            "$x ${getSomeString(R.string.week_result)}"
        } else {
            "$x ${getSomeString(R.string.weeks2_result)}"
        }
    }

    private fun addDataRequest(
        rate: String,
        initial: String,
        final: String,
        firstRadioButton: RadioButton,
        checkBoxButton: CheckBox,
        replenishment: String,
        result: String
    ): DataRequest {
        val monthAndWeek: String
        val monthReplenishment: String

        if (checkBoxButton.isChecked) monthReplenishment = replenishment
        else monthReplenishment = "-"

        if (firstRadioButton.isChecked) monthAndWeek = getSomeString(R.string.month)
        else monthAndWeek = getSomeString(R.string.week)

        return DataRequest(
            rate,
            initial,
            final,
            monthAndWeek,
            monthReplenishment,
            result
        )
    }

    private fun getSomeString(resource: Int): String {
        return getApplication<Application>().resources.getString(resource)
    }


}