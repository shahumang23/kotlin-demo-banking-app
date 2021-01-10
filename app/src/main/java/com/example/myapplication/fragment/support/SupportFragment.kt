package com.example.myapplication.fragment.support

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.MessagesRecyclerAdapter
import com.example.myapplication.listener.OnFragmentInteractionListener
import com.example.myapplication.model.message.MessageList
import kotlinx.android.synthetic.main.fragment_support.*
import kotlinx.android.synthetic.main.fragment_support.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

class SupportFragment: Fragment() {
    lateinit var adapter : MessagesRecyclerAdapter
    private var mListener: OnFragmentInteractionListener? = null
    lateinit var buttonSend: ImageButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_support, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = "Chat with Support"

        val supportMessageList = view.findViewById(R.id.messageList) as RecyclerView
        val messageListJsonString:String = readJsonFromKotlinFile("message_list.json")
        val messageDetailList: ArrayList<MessageList> = parseJsonStringToList(messageListJsonString)

        adapter = MessagesRecyclerAdapter(this.requireContext())
        populateMessageListItem(messageDetailList)

        supportMessageList.messageList.layoutManager = LinearLayoutManager(this.context)
        supportMessageList.messageList.adapter = adapter

        buttonSend = view.findViewById(R.id.btnSend) as ImageButton
        buttonSend.setOnClickListener {
            if(txtMessage.text.isNotEmpty()){
                adapter.setNewMessage(MessageList(4, txtMessage.text.toString(), Calendar.getInstance().timeInMillis.toString(), "user"
                ))
                resetInput()
            }
        }

        return view
    }

    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(TAG, uri)
        }
    }

    private fun resetInput() {
        // Clean text box
        txtMessage.text.clear()

        // Hide keyboard
        val inputManager = this.context ?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputManager?.hideSoftInputFromWindow(view?.windowToken, 0)
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

    companion object {
        val TAG = "Support Fragment"
    }

    private fun populateMessageListItem(messageList:ArrayList<MessageList>) {
        Log.d("MessageList", messageList.toString())
        adapter.setMessageList(messageList)
    }

    private fun parseJsonStringToList(jsonString: String): ArrayList<MessageList> {
        val messagesList: ArrayList<MessageList> = ArrayList<MessageList>(0)
        val jsonArray = JSONArray(jsonString)
        var i = 0
        val numIterations = jsonArray.length()
        while (i < numIterations) {
            val messageListsObject: JSONObject = jsonArray.getJSONObject(i)
            val id = messageListsObject.getInt("id")
            val messageDetails = messageListsObject.getString("message")
            val messageTime = messageListsObject.getString("time")
            val senderType = messageListsObject.getString("sender")
            messagesList.add(MessageList(id, messageDetails, messageTime, senderType))
            i++
        }
        return messagesList
    }

    private fun readJsonFromKotlinFile(assetsName: String): String {
        var inputString = ""

        try {
            val inputStream: InputStream = context!!.assets.open( assetsName)
            inputString = inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.d(SupportFragment.TAG, e.toString())
        }
        return inputString
    }
}