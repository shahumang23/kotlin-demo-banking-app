package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.listener.ItemMoveCallbackListener
import com.example.myapplication.listener.OnStartDragListener
import com.example.myapplication.model.AccountList
import kotlinx.android.synthetic.main.account_recyclerview_item.view.*
import java.text.NumberFormat
import java.util.*

class DragDropRecyclerAdapter(private val startDragListener: OnStartDragListener) :
    RecyclerView.Adapter<DragDropRecyclerAdapter.ItemViewHolder>(),
    ItemMoveCallbackListener.Listener {
    private var lists = emptyList<AccountList>().toMutableList()

    fun setAccountList(accountsList: List<AccountList>) {
        lists.addAll(accountsList)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val list = lists[position]
        holder.bind(list)

        holder.itemView.imageView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                this.startDragListener.onStartDrag(holder)
            }
            return@setOnTouchListener true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.account_recyclerview_item, parent, false)
        return ItemViewHolder(
            itemView
        )
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(response: AccountList) {
            itemView.textView.text = response.accountType
            itemView.accountNumberView.text = response.accountNumber
            itemView.amountView.text= NumberFormat.getCurrencyInstance().format(response.balance!!.toInt())
        }
    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(lists, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(lists, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onRowSelected(itemViewHolder: ItemViewHolder) {

    }

    override fun onRowClear(itemViewHolder: ItemViewHolder) {

    }
}
