package com.semba.weatherlogger.ui.homeScreen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.semba.weatherlogger.R
import com.semba.weatherlogger.base.BaseViewHolder
import com.semba.weatherlogger.data.entities.ForecastEntity
import com.semba.weatherlogger.ui.homeScreen.HomeNavigator
import kotlinx.android.synthetic.main.weather_list_item.view.*

/**
 * Created by SeMbA on 2020-01-03.
 */
class ForecastAdapter (private val delegate: HomeNavigator) : RecyclerView.Adapter<BaseViewHolder>() {

    private var items = ArrayList<ForecastEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun setForecastItems(newItems: ArrayList<ForecastEntity>)
    {
        val diffCallback = ForecastDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)

    }

    inner class ViewHolder(view: View) : BaseViewHolder(view)
    {
        private val temp = view.temp
        private val date = view.date
        private val detailsBtn = view.details_btn
        private val deleteBtn = view.delete_btn

        private val deleteClick = View.OnClickListener {
            delegate.deleteForecast(items[position])
        }

        private val detailsClick = View.OnClickListener {
            delegate.navigateToDetailsScreen(items[position])
        }

        override fun onBind(position: Int) {
            temp?.text = items[position].name
            date?.text = items[position].date

            detailsBtn?.setOnClickListener(detailsClick)
            deleteBtn?.setOnClickListener(deleteClick)
        }
    }
}