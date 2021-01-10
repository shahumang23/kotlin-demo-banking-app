package com.example.myapplication.adapter

import Transactions
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.transaction_recyclerview_item.view.*

/**
 * Created by Umang Shah on 26/04/20.
 * shahumang8@gmail.com
 */
class TransactionRecyclerAdapter(private val transactions : List<Transactions>): RecyclerView.Adapter<TransactionRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionRecyclerAdapter.ViewHolder {
        val v =  LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_recyclerview_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        var transcation = transactions[position]
        holder.vendor1Image(transcation.vendor)
        holder.vendorName.text = transcation.vendorName
        holder.amountValue.text = transcation.amount
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

       val vendorImage : ImageView = itemView.vendorImage
        val  vendorName : TextView = itemView.vendorName
        val amountValue : TextView = itemView.amoutTextView

        fun vendor1Image(type:String?){
            if(type.equals("grocery")){
                vendorImage.setImageResource(R.drawable.obe_ic_grocery_store)
            } else if(type.equals("travelling")){
                vendorImage.setImageResource(R.drawable.obe_ic_directions_bike)
            }else if(type.equals("restaurant")){
                vendorImage.setImageResource(R.drawable.obe_ic_restaurant)
            }else if(type.equals("drinks")){
                vendorImage.setImageResource(R.drawable.obe_ic_coffee)
            }
        }
    }
}