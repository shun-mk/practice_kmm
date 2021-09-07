package com.example.kmmpracticeapplication.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jetbrains.handson.kmm.shared.entity.RocketLaunchData

class LaunchesRvAdapter(var launches: List<RocketLaunchData>) :
    RecyclerView.Adapter<LaunchesRvAdapter.LaunchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_launch, parent, false)
            .run(::LaunchViewHolder)
    }

    override fun getItemCount(): Int = launches.count()

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        holder.bindData(launches[position])
    }

    inner class LaunchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val missionNameTextView = itemView.findViewById<TextView>(R.id.missionName)
        private val launchYearTextView = itemView.findViewById<TextView>(R.id.launchYear)
        private val launchSuccessTextView = itemView.findViewById<TextView>(R.id.launchSuccess)
        private val missionDetailsTextView = itemView.findViewById<TextView>(R.id.details)

        fun bindData(launch: RocketLaunchData) {
            val context = itemView.context
            missionNameTextView.text = context.getString(R.string.mission_name_field, launch.missionName)
            launchYearTextView.text = context.getString(R.string.launch_year_field, launch.launchYear.toString())
            missionDetailsTextView.text = context.getString(R.string.details_field, launch.details ?: "")

            val launchSuccess = launch.launchSuccess
            if (launchSuccess != null) {
                val successful = context.getString(R.string.successful)
                if (launchSuccess) {
                    launchSuccessTextView.text = successful
                    launchSuccessTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorSuccessful))
                } else {
                    launchSuccessTextView.text = successful
                    launchSuccessTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorUnsuccessful))
                }
            } else {
                launchSuccessTextView.text = context.getString(R.string.no_data)
                launchSuccessTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorNoData))
            }

        }

    }
}