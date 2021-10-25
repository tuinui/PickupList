package com.saran.akkaraviwat.pickuplist.pickuplist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saran.akkaraviwat.pickuplist.common.presentation.goneUnless
import com.saran.akkaraviwat.pickuplist.databinding.ListitemPickupBinding

class PickupListRecyclerAdapter : RecyclerView.Adapter<PickupListRecyclerAdapter.ViewHolder>() {

    private val items: MutableList<PickupItemUiModel> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun replaceData(newItems: List<PickupItemUiModel>) {
        items.clear()
        notifyDataSetChanged()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ListitemPickupBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(private val binding: ListitemPickupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PickupItemUiModel) {
            binding.apply {
                textviewListitemPickupName.text = item.alias
                textviewListitemPickupAddress.text = item.address
                textviewListitemPickupCity.text = item.city
            }
        }


        private fun setTextOrGone(){

        }

    }

}


