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
import by.vashkevich.calculator.MainViewModel
import by.vashkevich.calculator.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HistoryFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

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
            val adapter = AdapterDataRequest(it, viewModel, view.context)
            recDataRequest.adapter = adapter
            adapter.notifyDataSetChanged()
        }


        back.setOnClickListener {
            findNavController().navigate(R.id.backCalculatorFragment2)
        }

        clear.setOnClickListener {

            MaterialAlertDialogBuilder(
                view.context,
                R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Background
            )
                .setTitle(R.string.clear_all_data_in_recycler)
                .setNegativeButton("назад") { dialog, which ->
                    dialog.dismiss()
                }
                .setPositiveButton("очистить") { dialog, which ->
                    viewModel.clearAll()
                    viewModel.getAll()
                }
                .show()
        }

    }
}