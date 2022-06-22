package com.example.fitness_feip.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness_feip.ListItem
import com.example.fitness_feip.R

class RecyclerViewAdapter(private val clickListener: (ListItem.CardActivity) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val DATE_VIEW_TYPE = 0
        private const val CARD_VIEW_TYPE = 1
    }

    private val data: MutableList<ListItem> = mutableListOf()

    override fun getItemViewType(position: Int): Int = when (data[position]) {
        is ListItem.SplitDate -> DATE_VIEW_TYPE
        is ListItem.CardActivity -> CARD_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATE_VIEW_TYPE) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.date_component, parent, false)
            DateViewHolder(view)
        } else {
            val viewHolder =
                CardViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.cardview_component, parent, false)
                )
            viewHolder.itemView.setOnClickListener {
                clickListener(data[viewHolder.adapterPosition] as ListItem.CardActivity)
            }
            return viewHolder
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = data[position]) {
            is ListItem.SplitDate -> (holder as DateViewHolder).bind(item)
            is ListItem.CardActivity -> (holder as CardViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: List<ListItem>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateSplit = itemView.findViewById<TextView>(R.id.dateSplit)

        fun bind(date: ListItem.SplitDate) {
            dateSplit.text = date.date
        }
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val distance = itemView.findViewById<TextView>(R.id.distance)
        private val nickname = itemView.findViewById<TextView>(R.id.nickname)
        private val period = itemView.findViewById<TextView>(R.id.period)
        private val typeActivity = itemView.findViewById<TextView>(R.id.typeActivity)
        private val dateActivity = itemView.findViewById<TextView>(R.id.dateActivity)


        fun bind(card: ListItem.CardActivity) {
            distance.text = card.distance
            nickname.text = card.nickname
            period.text = card.period
            typeActivity.text = card.typeActivity
            dateActivity.text = card.dateActivity
        }
    }
}