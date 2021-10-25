package com.saran.akkaraviwat.pickuplist.common.presentation

import androidx.recyclerview.widget.DiffUtil

class CommonDiffUtil<T>(
    val new: List<T>,
    val old: List<T>,
    private val compare: ((old: T?, new: T?) -> Boolean)? = null
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = old.getOrNull(oldItemPosition)
        val newItem = new.getOrNull(newItemPosition)
        return compare?.invoke(oldItem, newItem) ?: newItem === oldItem
    }

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = old.getOrNull(oldItemPosition)
        val newItem = new.getOrNull(newItemPosition)
        return newItem == oldItem
    }
}
