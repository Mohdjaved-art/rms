package com.eapro.testingadv

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber


class DashboardAdapter(private val items: List<DashboardCard>) :
    RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    inner class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardTitle: TextView = view.findViewById(R.id.card_Title)
        val cardImage: ImageView = view.findViewById(R.id.card_Image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item_layout, parent, false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val item = items[position]
        holder.cardTitle.text = item.title
        holder.cardImage.setImageResource(item.imageResId)

        // Handle click event
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, item.targetActivity)

            // Pass the serial number to DataActivity, if available
            item.serialNumber?.let {
                intent.putExtra("serialNumber", it)
                println("myserialno ${item.serialNumber}")
            }

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}
