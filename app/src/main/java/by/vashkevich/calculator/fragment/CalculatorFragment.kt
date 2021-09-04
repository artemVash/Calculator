package by.vashkevich.calculator.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.vashkevich.calculator.MainViewModel
import by.vashkevich.calculator.R
import by.vashkevich.calculator.helpers.InputDataState
import com.google.android.material.textfield.TextInputEditText

class CalculatorFragment : Fragment() {

    private lateinit var interestRate: TextInputEditText
    private lateinit var initialCapital: TextInputEditText
    private lateinit var finalCapital: TextInputEditText
    private lateinit var sumMonth: TextInputEditText

    private lateinit var radioButtonWeek: RadioButton
    private lateinit var radioButtonMonth: RadioButton
    private lateinit var radioGroup:RadioGroup

    private lateinit var replenishmentCheckBox:CheckBox

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
        sumMonth = view.findViewById(R.id.sumMonth) // пополнение в месяц

        //RadioGroup
        radioGroup = view.findViewById(R.id.radio_group)

        //Поля RadioButton
        radioButtonWeek = view.findViewById(R.id.radioButtonWeek)
        radioButtonMonth = view.findViewById(R.id.radioButtonMonth)

        // Поле CheckBox
        replenishmentCheckBox = view.findViewById(R.id.replenishmentCheckBox)


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
            textCalculation.text = it
        }

        btnHistory.setOnClickListener {
            findNavController().navigate(R.id.showHistoryFragment)
            clearAllField()
            viewModel.clearTextCalculation()
        }

        btnCalculation.setOnClickListener {

            viewModel.dataReception(
                initialCapital = initialCapital.text.toString(),
                interestRate = interestRate.text.toString(),
                finalCapital = finalCapital.text.toString(),
                firstRadioButton = radioButtonMonth,
                secondRadioButton = radioButtonWeek,
                checkBoxButton = replenishmentCheckBox,
                monthReplenishment = vvv()
            )
        }

        viewModel.state.observe(viewLifecycleOwner, Observer<InputDataState>{ state ->

            when (state){
                is InputDataState.ErrorTextFieldsState -> {
                    Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                }
                is InputDataState.ErrorRadioButtonState -> {
                    Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                }
                is InputDataState.ErrorCheckBoxReplenishmentState ->{
                    Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                }
                is InputDataState.InputDataStateSuccess -> {

                }
                is InputDataState.DefaultState -> {

                }
            }

        })
    }

    private fun vvv():String{
        return if (replenishmentCheckBox.isChecked) sumMonth.text.toString()
        else "0.0"
    }

    private fun clearAllField(){
        interestRate.text?.clear()
        initialCapital.text?.clear()
        finalCapital.text?.clear()
        sumMonth.text?.clear()
        radioGroup.clearCheck()
        replenishmentCheckBox.isChecked = false

    }
}