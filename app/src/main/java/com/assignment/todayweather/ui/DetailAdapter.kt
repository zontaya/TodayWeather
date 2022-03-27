package com.assignment.todayweather.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assignment.todayweather.R
import com.assignment.todayweather.data.remote.model.Current
import com.squareup.picasso.Picasso
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


class DetailAdapter : ListAdapter<Current, DetailAdapter.ViewHolder>(DataDiffCallback()) {

    var units = "metric"
    lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tempTime: TextView = view.findViewById(R.id.temp_time)
        val temp: TextView = view.findViewById(R.id.temp)
        val icon: ImageView = view.findViewById(R.id.icon)
        val tempDes: TextView = view.findViewById(R.id.temp_des)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        context = viewGroup.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.list_detail_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val detail = getItem(position)
        val iconName = detail.weather[0].icon
        val temp = String.format(
            context.getString(R.string.temp),
            detail.temp,
            if (units == "metric") "°C" else "°F"
        )
        viewHolder.temp.text = temp

        val dt = convertDate(detail.dt)
        var minute = "${dt.minute}"
        var hour = "${dt.hour}"

        if (dt.minute < 10) {
            minute = "0${dt.minute}"
        }
        if (dt.hour < 10) {
            hour = "0${dt.hour}"
        }
        viewHolder.tempTime.text = String.format(
            context.getString(R.string.temp_detail_dt),
            dt.date.toString(), hour, minute
        )

        viewHolder.tempDes.text = detail.weather[0].description

        Picasso.get().load(iconName).into(viewHolder.icon)
    }

    private fun convertDate(dateInMilliseconds: Long): LocalDateTime {
        val tz = TimeZone.currentSystemDefault()
        val currentMoment = Instant.fromEpochSeconds(dateInMilliseconds)
        return currentMoment.toLocalDateTime(tz)


    }

    private class DataDiffCallback : DiffUtil.ItemCallback<Current>() {
        override fun areItemsTheSame(oldItem: Current, newItem: Current) =
            oldItem.dt == newItem.dt

        override fun areContentsTheSame(oldItem: Current, newItem: Current) =
            oldItem == newItem
    }

}



