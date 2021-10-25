package com.saran.akkaraviwat.pickuplist.common.presentation

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalSpaceItemDecoration
private constructor(private val verticalSpaceHeight: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = verticalSpaceHeight
    }

    companion object {
        fun create(heightInDp: Int): VerticalSpaceItemDecoration {
            return VerticalSpaceItemDecoration(heightInDp.dpToPx())
        }
    }
}
