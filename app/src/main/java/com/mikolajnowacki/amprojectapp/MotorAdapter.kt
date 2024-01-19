package com.mikolajnowacki.amprojectapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MotorAdapter(private val items: MutableList<Motor>) :
    RecyclerView.Adapter<MotorAdapter.ItemViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_motor_monitor, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_motor_name)
        private val voltageTextView: TextView = itemView.findViewById(R.id.tv_voltage_rating)
        private val maxRPMTextView: TextView = itemView.findViewById(R.id.tv_max_rpm)
        private val overloadTextView: TextView = itemView.findViewById(R.id.tv_max_overload)
        private val currentRPMTextView: TextView = itemView.findViewById(R.id.tv_current_rpm_value)
        private val statusTextView: TextView = itemView.findViewById(R.id.tv_status_value)
        private val statusImageView: ImageView = itemView.findViewById(R.id.image_view_status)

        fun bind(item: Motor) {
            nameTextView.text = item.name
            voltageTextView.text = item.voltageRating
            maxRPMTextView.text = item.maxRPM
            currentRPMTextView.text = item.currentRPM
            overloadTextView.text = item.maxLoad
            statusTextView.text = item.status

            if (item.status == "offline") {
                statusImageView.setImageResource(R.drawable.circle_red)
            } else if (item.status == "online") {
                statusImageView.setImageResource(R.drawable.circle_green)
            }


        }

    }

}