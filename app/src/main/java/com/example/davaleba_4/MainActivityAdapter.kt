package com.example.davaleba_4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


typealias onClicked = (v: ItemsViewModel) -> Unit

class MainActivityAdapter(private val mList: List<ItemsViewModel>) :
    RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {
    lateinit var onClicked: onClicked

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val card: View = itemView.findViewById(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_main_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        holder.textView.text = itemsViewModel.text
        holder.card.setOnClickListener {
            onClicked(itemsViewModel)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }


}