package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.message.MessageList
import kotlinx.android.synthetic.main.message_bubble_shape.view.*
import kotlinx.android.synthetic.main.reply_bubble_shape.view.*

private const val VIEW_TYPE_MY_MESSAGE = 1
private const val VIEW_TYPE_SUPPORT_MESSAGE = 2

class MessagesRecyclerAdapter(val context: Context) :
    RecyclerView.Adapter<MessageViewHolder>() {


    private var lists = emptyList<MessageList>().toMutableList()

    fun setMessageList(messagesList: List<MessageList>) {
        lists.addAll(messagesList)
    }

    fun setNewMessage(messagesList: MessageList) {
        lists.add(messagesList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = lists.get(position)

        return if ("user" == message.sender) {
            VIEW_TYPE_MY_MESSAGE
        } else {
            VIEW_TYPE_SUPPORT_MESSAGE
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val list = lists[position]
        holder.bind(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if (viewType == VIEW_TYPE_MY_MESSAGE) {
            MyMessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.message_bubble_shape, parent, false)
            )
        } else {
            SupportMessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.reply_bubble_shape, parent, false)
            )
        }
    }

    inner class MyMessageViewHolder(view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.message
        //private var timeText: TextView = view.txtMyMessageTime

        override fun bind(message: MessageList) {
            messageText.text = message.message
            //timeText.text = DateUtils.fromMillisToTimeString(message.time)
        }
    }

    inner class SupportMessageViewHolder(view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.supportMessage
        //private var userText: TextView = view.txtOtherUser
        //private var timeText: TextView = view.txtOtherMessageTime

        override fun bind(message: MessageList) {
            messageText.text = message.message
            //userText.text = message.user
            //timeText.text = DateUtils.fromMillisToTimeString(message.time)
        }
    }

}

open class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message: MessageList) {}
}