package by.vashkevich.calculator.repository

import android.widget.CheckBox
import android.widget.RadioButton
import by.vashkevich.calculator.models.PaymentRepository

class PaymentRepositoryImp : PaymentRepository {

    override suspend fun payment(
        initialCapital: String,
        interestRate: String,
        finalCapital: String,
        firstRadioButton: RadioButton,
        secondRadioButton: RadioButton,
        checkBoxButton: CheckBox,
        monthReplenishment: String
    ): Double {

        val numInterestRate = interestRate.toDouble()
        var numInitialCapital = initialCapital.toDouble()
        val numFinalCapital = finalCapital.toDouble()

        var daysCounter = 0
        var weeks = 0.0

        if (checkBoxButton.isChecked) {

            val numMonthReplenishment = monthReplenishment.toDouble()

            while (numInitialCapital < numFinalCapital) {

                daysCounter++

                if (daysCounter % 30 == 0 && firstRadioButton.isChecked) {
                    numInitialCapital += numInitialCapital / 100 * numInterestRate // ежемесечная капитализация
                    weeks += 4.3; // 4.3 количество недель в месяце
                } else if (daysCounter % 7 == 0 && secondRadioButton.isChecked) {
                    numInitialCapital += numInitialCapital / 100 * numInterestRate // еженедельная капитализация
                    weeks++;
                }

                if (checkBoxButton.isChecked && daysCounter % 30 == 0) {
                    val numSumMonth = (numMonthReplenishment)
                    numInitialCapital += numSumMonth;
                }
            }
            return weeks
        } else {

            while (numInitialCapital < numFinalCapital) {

                daysCounter++

                if (daysCounter % 30 == 0 && firstRadioButton.isChecked) {
                    numInitialCapital += numInitialCapital / 100 * numInterestRate // ежемесечная капитализация
                    weeks += 4.3; // 4.3 количество недель в месяце
                } else if (daysCounter % 7 == 0 && secondRadioButton.isChecked) {
                    numInitialCapital += numInitialCapital / 100 * numInterestRate // еженедельная капитализация
                    weeks++;
                }
            }
            return weeks
        }
    }
}