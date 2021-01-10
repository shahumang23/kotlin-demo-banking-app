package com.example.myapplication.fragment.notification

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.NotificationRecyclerAdapter
import com.example.myapplication.callback.SwipeToDeleteCallback
import com.example.myapplication.listener.NotificationClickListener
import com.example.myapplication.listener.OnFragmentInteractionListener
import com.example.myapplication.model.notification.NotificationList
import kotlinx.android.synthetic.main.fragment_notification.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream


class NotificationFragment: Fragment(), NotificationClickListener {

    lateinit var adapter : NotificationRecyclerAdapter
    private var mListener: OnFragmentInteractionListener? = null
    var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notification, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = "Notification"

        val notificationViewList = view.findViewById(R.id.notificationLists) as RecyclerView
        val notificationListJsonString:String = readJsonFromKotlinFile("notification_list.json")
        val notificationList: ArrayList<NotificationList> = parseJsonStringToList(notificationListJsonString)

        adapter = NotificationRecyclerAdapter(this.requireContext(), this)

        populateNotificationListItem(notificationList)

        notificationViewList.notificationLists.layoutManager = LinearLayoutManager(this.context)
        notificationViewList.notificationLists.adapter = adapter

        val swipeHandler = object : SwipeToDeleteCallback(this.context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(notificationViewList)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(TAG, uri)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    private fun populateNotificationListItem(notificationList:ArrayList<NotificationList>) {
        adapter.setNotificationList(notificationList)
    }

    companion object {
        val TAG = "Notification Fragment"
    }

    private fun parseJsonStringToList(jsonString: String): ArrayList<NotificationList> {
        val notificationsList: ArrayList<NotificationList> = ArrayList<NotificationList>(0)
        val jsonArray = JSONArray(jsonString)
        var i = 0
        val numIterations = jsonArray.length()
        while (i < numIterations) {
            val messageListsObject: JSONObject = jsonArray.getJSONObject(i)
            val id = messageListsObject.getInt("id")
            val body = messageListsObject.getString("body")
            val subject = messageListsObject.getString("subject")
            val dateTime = messageListsObject.getString("dateTime")
            val readableStatus = messageListsObject.getBoolean("readableStatus")
            notificationsList.add(
                NotificationList(
                    id,
                    body,
                    subject,
                    dateTime,
                    readableStatus
                )
            )
            i++
        }
        return notificationsList
    }

    private fun readJsonFromKotlinFile(assetsName: String): String {
        var inputString = ""

        try {
            val inputStream: InputStream = context!!.assets.open( assetsName)
            inputString = inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.d(NotificationFragment.TAG, e.toString())
        }
        return inputString
    }

    override fun onNotificationClickListener(data: NotificationList) {
        val bundle = bundleOf(
            "body" to data.body,
            "subject" to data.subject,
            "timeZone" to data.dateTime
        )
        navController!!.navigate(
            R.id.action_notificationFragment_to_notificationDetailFragment,
            bundle
        )
    }
}