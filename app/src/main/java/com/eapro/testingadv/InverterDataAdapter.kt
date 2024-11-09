package com.eapro.testingadv
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class InverterDataAdapter(private var dataList: MutableList<CustomerDataResponse>) : RecyclerView.Adapter<InverterDataAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.date)
        val tvTime: TextView = itemView.findViewById(R.id.time)
        val tvCapacity: TextView = itemView.findViewById(R.id.ipvolt)
        val OP: TextView = itemView.findViewById(R.id.op_volt)
        val BP: TextView = itemView.findViewById(R.id.batry_volt)
        val CC: TextView = itemView.findViewById(R.id.inv_cc)
        val tvLoad:TextView = itemView.findViewById(R.id.inv_load)
        val tvScc:TextView = itemView.findViewById(R.id.inv_scc)
        val tvKWH:TextView = itemView.findViewById(R.id.inv_solarkwh)
        val tv_ONNOFF:TextView = itemView.findViewById(R.id.onoff)
        val tvSavingLevel:TextView = itemView.findViewById(R.id.savingLevel)
        val tvMode:TextView = itemView.findViewById(R.id.mode)
        val tvAlerts:TextView = itemView.findViewById(R.id.alerts)
        val tvDisplayTime:TextView = itemView.findViewById(R.id.inv_displaytime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_inverter_data, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        Log.d("InverterDataAdapter", "Binding data at position $position: $data")
        holder.tvDate.text = data.Date
        holder.tvTime.text = data.Time
        holder.tvCapacity.text = data.inputVolt
        holder.OP.text = data.outputVolt
        holder.BP.text = data.batteryVolt
        holder.tvLoad.text = data.loadPercentage
        holder.tvScc.text = data.solarCurrentCapacity
        holder.tvKWH.text = data.solarKWH
        holder.tv_ONNOFF.text = data.onOff
        holder.tvSavingLevel.text = data.savingLevel
        holder.tvMode.text = data.mode
        holder.tvAlerts.text = data.alerts
        holder.tvDisplayTime.text = data.inverterDisplayTime
    }

    override fun getItemCount() = dataList.size

    // Function to add new data dynamically
//    fun addRow(newData: CustomerDataResponse) {
//        dataList.add(newData)
//        notifyItemInserted(dataList.size - 1)  // Notify adapter to update the view for new item
//    }

    // Function to update the entire data list dynamically
    fun updateData(newDataList: List<CustomerDataResponse>) {
        dataList.clear()
        dataList.addAll(newDataList)
        notifyDataSetChanged()  // Notify the adapter that data has changed
    }
}

