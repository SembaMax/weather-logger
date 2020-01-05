package com.semba.weatherlogger.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.semba.weatherlogger.R

/**
 * Created by SeMbA on 2020-01-02.
 */
class MarginItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private var margin = 0

    init {
        margin = context.resources.getDimensionPixelSize(R.dimen.item_margin)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(margin, margin, margin, margin)
    }
}