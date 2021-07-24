package by.vashkevich.calculator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.vashkevich.calculator.dataBase.entity.DataRequest
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AdapterDataRequest(val list:List<DataRequest>,val viewModel: MainViewModel,context: Context) : RecyclerView.Adapter<AdapterDataRequest.CardViewHolder>() {

    val contextR = context

    inner class CardViewHolder(private val view: View): RecyclerView.ViewHolder(view){

        fun setData(itemView: View, position: Int){

            val data = list[position]

            itemView.findViewById<TextView>(R.id.item_interest_rate).text = "${data.rate}%"
            itemView.findViewById<TextView>(R.id.item_initial_capital).text = data.initialCapital
            itemView.findViewById<TextView>(R.id.item_final_capital).text = data.finalCapital
            itemView.findViewById<TextView>(R.id.item_type_of_capitalization).text = data.typeOfCapitalization
            itemView.findViewById<TextView>(R.id.item_replenishment_in_a_month).text = data.replenishmentInAMonth
            itemView.findViewById<TextView>(R.id.item_result).text = data.result

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history_recyclerview, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.setData(holder.itemView,position)
    }

    override fun getItemCount(): Int {
        return list.size
    }


}
