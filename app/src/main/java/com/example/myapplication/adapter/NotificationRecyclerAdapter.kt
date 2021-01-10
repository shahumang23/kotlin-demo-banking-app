package com.example.myapplication.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.listener.NotificationClickListener
import com.example.myapplication.model.notification.NotificationList
import kotlinx.android.synthetic.main.notification_recyclerview_item.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Umang Shah on 25/05/20.
 * shahumang8@gmail.com
 */
class NotificationRecyclerAdapter(val context: Context, private val notificationClickListener: NotificationClickListener):
    RecyclerView.Adapter<NotificationViewHolder>() {

    private var lists = emptyList<NotificationList>().toMutableList()

    fun setNotificationList(notificationsList: List<NotificationList>) {
        lists.addAll(notificationsList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationListViewHolder(LayoutInflater.from(context).inflate(R.layout.notification_recyclerview_item, parent, false))
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val list = lists[position]
        holder.bind(list)
        holder.itemView.setOnClickListener{
            notificationClickListener.onNotificationClickListener(list)
        }
    }

    fun removeAt(position: Int) {
        lists.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class NotificationListViewHolder(view: View) : NotificationViewHolder(view) {
        private var headerText: TextView = view.notificationHeaderView
        private var bodyText: TextView = view.notificationDetailTxtView
        private var dateTimeText: TextView = view.notificationDateTimeTextView

        override fun bind(notification: NotificationList) {
            headerText.text = notification.subject
            bodyText.text = notification.body
            dateTimeText.text = dateUtil(notification.dateTime)
        }
    }

    fun dateUtil(date: String): String{
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        var convertedDate: Date? = null
        var formattedDate: String? = null
        val localDate = Calendar.getInstance().time
        Log.d("LocalDate", localDate.toString())
        try {
            convertedDate = sdf.parse(date)
            formattedDate = SimpleDateFormat("HH:mm aa").format(convertedDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formattedDate!!
    }
}

open class NotificationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(notification: NotificationList){}
}
