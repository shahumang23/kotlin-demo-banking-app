package com.example.myapplication.adapter

import TransactionList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.transaction_layout_header_item.view.*

class TransactionListRecyclerAdapter() : RecyclerView.Adapter<TransactionListRecyclerAdapter.ViewHolder>() {

    private var lists = emptyList<TransactionList>().toMutableList()

    private val viewPool = RecyclerView.RecycledViewPool()

    fun setTransactionList(transactionList: List<TransactionList>) {
        lists.addAll(transactionList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_layout_header_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = lists[position]
        holder.headerText.text = list.date
        holder.recyclerView.apply {
            layoutManager = LinearLayoutManager(holder.recyclerView.context, RecyclerView.VERTICAL, false)
            adapter = TransactionRecyclerAdapter(list.transactions)
            setRecycledViewPool(viewPool)
        }
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val recyclerView : RecyclerView = itemView.rv_transaction
        val  headerText : TextView = itemView.text_header
    }
}