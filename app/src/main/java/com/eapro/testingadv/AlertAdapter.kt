package com.eapro.testingadv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class AlertAdapter(private val alertList: List<AlertResponse>) :
    RecyclerView.Adapter<AlertAdapter.AlertViewHolder>() {

    class AlertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvInverterAlert: TextView = itemView.findViewById(R.id.InvAlert)
        val tvDate: TextView = itemView.findViewById(R.id.Date)
        val tvTime: TextView = itemView.findViewById(R.id.Time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.alert_item_layout, parent, false)
        return AlertViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        val alert = alertList[position]
        holder.tvInverterAlert.text = alert.inverterAlert
        holder.tvDate.text = alert.Date
        holder.tvTime.text = alert.Time
    }

    override fun getItemCount() = alertList.size
}
