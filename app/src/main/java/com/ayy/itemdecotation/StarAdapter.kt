package com.ayy.itemdecotation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StarAdapter(val data: List<Star>?) : RecyclerView.Adapter<StarAdapter.StarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return StarViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: StarViewHolder, position: Int) {
        holder.tv.text = data?.get(position)?.name
    }

    inner class StarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv: TextView = itemView.findViewById(R.id.tv)
    }
}