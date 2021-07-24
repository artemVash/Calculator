package by.vashkevich.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Поля ввода
        val interestRate = findViewById<TextInputEditText>(R.id.interestRate) // процент
        val initialCapital = findViewById<TextInputEditText>(R.id.initialCapital) // начальная сумма
        val finalCapital = findViewById<TextInputEditText>(R.id.finalCapital)     // ожидаеммая сумма
        val sumMonth = findViewById<TextInputEditText>(R.id.sumMonth)             // пополнение в месяц


        //Поля RadioButton
        val radioButtonWeek = findViewById<RadioButton>(R.id.radioButtonWeek)
        val radioButtonMonth = findViewById<RadioButton>(R.id.radioButtonMonth)

        // Поле CheckBox
        val replenishmentCheckBox = findViewById<CheckBox>(R.id.replenishmentCheckBox)


        //Поле вывода
        val textCalculation = findViewById<TextView>(R.id.textCalculation)

        //Кнопка
        val btnCalculation = findViewById<TextView>(R.id.btnCalculation)


        replenishmentCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                sumMonth.isEnabled = true
            } else {
                sumMonth.isEnabled = false
                sumMonth.text?.clear()
            }
        }
        viewModel.textCalculation.observe(this){
            textCalculation.text = "$it Месяцев "
        }

        btnCalculation.setOnClickListener {

            var daysCounter = 0
            var weeks = 0.0

            if (initialCapital.text.isNullOrEmpty() ||
                    interestRate.text.isNullOrEmpty() ||
                    finalCapital.text.isNullOrEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_LONG).show()
            } else {

                if (radioButtonMonth.isChecked || radioButtonWeek.isChecked) {

                    val numInterestRate = interestRate.text.toString().toDouble()
                    var numInitialCapital = initialCapital.text.toString().toDouble()
                    val numFinalCapital = finalCapital.text.toString().toDouble()

                    while (numInitialCapital < numFinalCapital) {

                        daysCounter++

                        if (daysCounter % 30 == 0 && radioButtonMonth.isChecked) {
                            numInitialCapital += numInitialCapital / 100 * numInterestRate // ежемесечная капитализация
                            weeks += 4.3; // 4.3 количество недель в месяце
                        } else if (daysCounter % 7 == 0 && radioButtonWeek.isChecked) {
                            numInitialCapital += numInitialCapital / 100 * numInterestRate // еженедельная капитализация
                            weeks++;
                        }

                        if (replenishmentCheckBox.isChecked && daysCounter % 30 == 0) {
                            val numSumMonth = (sumMonth.text.toString()).toDouble()
                            numInitialCapital += numSumMonth;
                        }

                    }
                } else {
                    Toast.makeText(this, "Выберите тип капитализации", Toast.LENGTH_LONG).show()
                }
            }
            val month = weeks / 4.3

            val monthEnd: String = String.format("%.2f", month)
            viewModel.setTextCalculation(monthEnd)


        }

    }
}