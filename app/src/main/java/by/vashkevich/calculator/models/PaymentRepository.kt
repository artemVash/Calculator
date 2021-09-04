package by.vashkevich.calculator.models

import android.widget.CheckBox
import android.widget.RadioButton

interface PaymentRepository {

    suspend fun payment(
        initialCapital: String,
        interestRate: String,
        finalCapital: String,
        firstRadioButton: RadioButton,
        secondRadioButton: RadioButton,
        checkBoxButton: CheckBox,
        monthReplenishment: String):Double
}