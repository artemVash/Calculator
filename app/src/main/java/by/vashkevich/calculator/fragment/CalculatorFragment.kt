package by.vashkevich.calculator.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.vashkevich.calculator.MainViewModel
import by.vashkevich.calculator.R
import com.google.android.material.textfield.TextInputEditText

class CalculatorFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
                .create(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Поля ввода
        val interestRate = view.findViewById<TextInputEditText>(R.id.interestRate) // процент
        val initialCapital = view.findViewById<TextInputEditText>(R.id.initialCapital) // начальная сумма
        val finalCapital = view.findViewById<TextInputEditText>(R.id.finalCapital)     // ожидаеммая сумма
        val sumMonth = view.findViewById<TextInputEditText>(R.id.sumMonth)             // пополнение в месяц


        //Поля RadioButton
        val radioButtonWeek = view.findViewById<RadioButton>(R.id.radioButtonWeek)
        val radioButtonMonth = view.findViewById<RadioButton>(R.id.radioButtonMonth)

        // Поле CheckBox
        val replenishmentCheckBox = view.findViewById<CheckBox>(R.id.replenishmentCheckBox)


        //Поле вывода
        val textCalculation = view.findViewById<TextView>(R.id.textCalculation)

        //Кнопки
        val btnCalculation = view.findViewById<TextView>(R.id.btnCalculation)
        val btnHistory = view.findViewById<TextView>(R.id.btnHistory)


        replenishmentCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                sumMonth.isEnabled = true
            } else {
                sumMonth.isEnabled = false
                sumMonth.text?.clear()
            }
        }
        viewModel.textCalculation.observe(requireActivity()) {
            textCalculation.text = "$it Месяцев "
        }

        btnHistory.setOnClickListener {
            findNavController().navigate(R.id.showHistoryFragment)
        }

        btnCalculation.setOnClickListener {

            var daysCounter = 0
            var weeks = 0.0

            if (initialCapital.text.isNullOrEmpty() ||
                    interestRate.text.isNullOrEmpty() ||
                    finalCapital.text.isNullOrEmpty()) {
                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_LONG).show()
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
                    Toast.makeText(context, "Выберите тип капитализации", Toast.LENGTH_LONG).show()
                }
            }
            val month = weeks / 4.3

            val monthEnd: String = String.format("%.2f", month)
            viewModel.setTextCalculation(monthEnd)

        }
    }
}