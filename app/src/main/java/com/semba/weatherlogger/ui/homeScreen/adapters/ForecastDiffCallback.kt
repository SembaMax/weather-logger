package com.semba.weatherlogger.ui.homeScreen.adapters

import androidx.recyclerview.widget.DiffUtil
import com.semba.weatherlogger.data.entities.ForecastEntity

/**
 * Created by SeMbA on 2020-01-05.
 */
open class ForecastDiffCallback(private val oldList: List<ForecastEntity>, private val newList: List<ForecastEntity>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id === newList.get(newItemPosition).id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition].id == newList.get(newPosition).id
    }


    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}