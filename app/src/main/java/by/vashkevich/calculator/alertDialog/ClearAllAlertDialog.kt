package by.vashkevich.calculator.alertDialog

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import by.vashkevich.calculator.viewModel.MainViewModel
import by.vashkevich.calculator.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ClearAllAlertDialog(context : Context) : MaterialAlertDialogBuilder(context) {

    private val viewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(context.applicationContext as Application)
            .create(MainViewModel::class.java)
    }

    override fun create(): AlertDialog {
        return MaterialAlertDialogBuilder(
            context,
            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Background
        )
            .setTitle(R.string.clear_all_data_in_recycler)
            .setNegativeButton(context.resources.getText(R.string.back)) { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton(context.resources.getText(R.string.clear)) { dialog, which ->
                viewModel.clearAll()
                viewModel.getAll()
            }
            .show()
    }
}