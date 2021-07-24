package by.vashkevich.calculator.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.vashkevich.calculator.AdapterDataRequest
import by.vashkevich.calculator.MainViewModel
import by.vashkevich.calculator.R

class HistoryFragment : Fragment() {

    private val viewModel by lazy{
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_history,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //recycler
        val recDataRequest = view.findViewById<RecyclerView>(R.id.data_recycler)

        //Button
        val back = view.findViewById<TextView>(R.id.btn_back_calculator_fragment)
        val clear = view.findViewById<TextView>(R.id.btn_clear_all_db)

        back.setOnClickListener {
            findNavController().navigate(R.id.backCalculatorFragment2)
        }

        clear.setOnClickListener {
            //
//                MaterialAlertDialogBuilder(contextR)
//
//                        .setNegativeButton("back") { dialog, which ->
//                            dialog.dismiss()
//                        }
//                        .setPositiveButton("delete") { dialog, which ->
//                            viewModelHW7.delete(card)
//                            viewModelHW7.getAll()
//                        }
//                        .show()
        }

        viewModel.getAll()

        viewModel.allDataRequest.observe(requireActivity()){
            val adapter = AdapterDataRequest(it,viewModel,view.context)
            recDataRequest.adapter = adapter
        }


    }
}