package by.vashkevich.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Поля ввода
        val interestRate = findViewById<TextInputEditText>(R.id.interestRate)     // процент
        val initialCapital = findViewById<TextInputEditText>(R.id.initialCapital) // начальная сумма
        val finalCapital = findViewById<TextInputEditText>(R.id.finalCapital)     // ожидаеммая сумма
        var sumMonth: EditText             // пополнение в месяц

        //Поля RadioButton
        val radioButtonWeek = findViewById<RadioButton>(R.id.radioButtonWeek)
        val radioButtonMonth = findViewById<RadioButton>(R.id.radioButtonMonth)

        // Поле CheckBox
        val replenishmentCheckBox = findViewById<CheckBox>(R.id.replenishmentCheckBox)

        //Поле вывода
        var textCalculation = findViewById<TextView>(R.id.textCalculation)

        //Кнопка
        val buttonCalculation = findViewById<Button>(R.id.buttonCalculation)



        buttonCalculation.setOnClickListener {

            val numInterestRate = interestRate.text.toString().toDouble()
            var numInitialCapital = initialCapital.text.toString().toDouble()
            var numFinalCapital = finalCapital.text.toString().toDouble()

            var daysCounter = 0
            var weeks = 0.0



            if (numInitialCapital != null && numInterestRate != null && numFinalCapital != null) {
                while (numInitialCapital < numFinalCapital!!) {

                    daysCounter++

                    if (daysCounter % 30 == 0 && radioButtonMonth.isChecked) {
                        numInitialCapital += numInitialCapital / 100 * numInterestRate!! // ежемесечная капитализация
                        weeks += 4.3; // 4.3 количество недель в месяце
                    } else if (daysCounter % 7 == 0 && radioButtonWeek.isChecked) {
                        numInitialCapital += numInitialCapital / 100 * numInterestRate!! // еженедельная капитализация
                        weeks++;
                    }

                    if (replenishmentCheckBox.isChecked && daysCounter % 30 == 0) {
                        sumMonth = findViewById<TextInputEditText>(R.id.sumMonth)
                        val numSumMonth = (sumMonth.text.toString()).toDouble()
                        numInitialCapital += numSumMonth;
                    }

                }
            }
            val month = weeks / 4.3



            textCalculation.text = "$month Месяцев "

        }

    }
}