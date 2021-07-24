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
import by.vashkevich.calculator.dataBase.entity.DataRequest
import com.google.android.material.textfield.TextInputEditText

class CalculatorFragment : Fragment() {

    private lateinit var interestRate: TextInputEditText
    private lateinit var initialCapital: TextInputEditText
    private lateinit var finalCapital: TextInputEditText
    private lateinit var sumMonth: TextInputEditText

    private lateinit var radioButtonWeek: RadioButton
    private lateinit var radioButtonMonth: RadioButton

    private val viewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
            .create(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAll()

        //Поля ввода
        interestRate = view.findViewById(R.id.interestRate) // процент
        initialCapital = view.findViewById(R.id.initialCapital) // начальная сумма
        finalCapital = view.findViewById(R.id.finalCapital)     // ожидаеммая сумма
        sumMonth = view.findViewById(R.id.sumMonth)             // пополнение в месяц


        //Поля RadioButton
        radioButtonWeek = view.findViewById<RadioButton>(R.id.radioButtonWeek)
        radioButtonMonth = view.findViewById<RadioButton>(R.id.radioButtonMonth)

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

            var checkedRadioButton = false
            var checkedCheckBox = false


            if (initialCapital.text.isNullOrEmpty() ||
                interestRate.text.isNullOrEmpty() ||
                finalCapital.text.isNullOrEmpty()
            ) {
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
                            checkedRadioButton = true
                        } else if (daysCounter % 7 == 0 && radioButtonWeek.isChecked) {
                            numInitialCapital += numInitialCapital / 100 * numInterestRate // еженедельная капитализация
                            weeks++;
                        }

                        if (replenishmentCheckBox.isChecked && daysCounter % 30 == 0) {
                            val numSumMonth = (sumMonth.text.toString()).toDouble()
                            numInitialCapital += numSumMonth;
                            checkedCheckBox = true
                        }

                    }
                } else {
                    Toast.makeText(context, "Выберите тип капитализации", Toast.LENGTH_LONG).show()
                }
            }
            val month = weeks / 4.3
            val monthEnd: String = String.format("%.2f", month)

            viewModel.insert(
                addDataRequest(
                    interestRate.text.toString(),
                    initialCapital.text.toString(),
                    finalCapital.text.toString(),
                    checkedRadioButton,
                    checkedCheckBox,
                    monthEnd
                )
            )
            viewModel.setTextCalculation(monthEnd)

        }
    }

    private fun addDataRequest(
        rate: String,
        initial: String,
        final: String,
        month: Boolean,
        monthSum: Boolean,
        result: String
    ): DataRequest {
        val monthAndWeek: String
        val monthReplenishment: String

        if (monthSum) monthReplenishment = sumMonth.text.toString()
        else monthReplenishment = "-"

        if (month) monthAndWeek = "Месяц"
        else monthAndWeek = "Неделя"

        return DataRequest(
            rate,
            initial,
            final,
            monthAndWeek,
            monthReplenishment,
            result
        )
    }
}