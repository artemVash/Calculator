package by.vashkevich.calculator.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.vashkevich.calculator.AdapterDataRequest
import by.vashkevich.calculator.viewModel.MainViewModel
import by.vashkevich.calculator.R
import by.vashkevich.calculator.alertDialog.ClearAllAlertDialog

class HistoryFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    private val clearAllAlertDialog = ClearAllAlertDialog(requireContext())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //recycler
        val recDataRequest = view.findViewById<RecyclerView>(R.id.data_recycler)

        //Button
        val back = view.findViewById<ImageView>(R.id.btn_back_calculator_fragment)
        val clear = view.findViewById<ImageView>(R.id.btn_clear_all_db)

        viewModel.getAll()

        viewModel.allDataRequest.observe(requireActivity()) {
            val adapter = AdapterDataRequest(it, view.context)
            recDataRequest.adapter = adapter
            adapter.notifyDataSetChanged()
        }


        back.setOnClickListener {
            findNavController().popBackStack()
        }

        clear.setOnClickListener {
            clearAllAlertDialog.show()
        }

    }
}