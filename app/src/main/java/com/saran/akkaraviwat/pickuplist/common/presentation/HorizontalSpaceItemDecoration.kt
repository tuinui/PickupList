package com.saran.akkaraviwat.pickuplist.common.presentation

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Recyclerview decoration class to add spacing between items
 */
class HorizontalSpaceItemDecoration private constructor(private val spacePx: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) > 0) {
            outRect.left = spacePx
        }
    }

    companion object {
        fun create(spaceInDp: Int): HorizontalSpaceItemDecoration {
            return HorizontalSpaceItemDecoration(spaceInDp.dpToPx())
        }
    }
}
