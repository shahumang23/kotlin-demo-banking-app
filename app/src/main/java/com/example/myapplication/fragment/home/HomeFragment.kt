package com.example.myapplication.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.DragDropRecyclerAdapter
import com.example.myapplication.listener.ItemMoveCallbackListener
import com.example.myapplication.listener.OnStartDragListener
import com.example.myapplication.model.AccountList
import kotlinx.android.synthetic.main.home_fragment.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HomeFragment : Fragment(), OnStartDragListener {
    lateinit var adapter: DragDropRecyclerAdapter
    lateinit var touchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.home_fragment, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = "Home"

        var mainMenu = view.findViewById(R.id.recyclerView) as RecyclerView

        adapter = DragDropRecyclerAdapter(this)
        val jsonString:String = readJsonFromKotlinFile()
        val accountList : ArrayList<AccountList> = parseJsonStringToList(jsonString)
        populateListItem(accountList)

        val callback: ItemTouchHelper.Callback =
            ItemMoveCallbackListener(adapter)

        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(mainMenu)

        mainMenu.recyclerView.layoutManager = LinearLayoutManager(this.context)
        mainMenu.recyclerView.adapter = adapter
        return view
    }


    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        touchHelper.startDrag(viewHolder)
    }

    private fun populateListItem(accountList : ArrayList<AccountList>) {
        adapter.setAccountList(accountList)
    }

    companion object {
        val TAG = "Home Fragment"
    }

    private fun parseJsonStringToList(jsonString: String): ArrayList<AccountList> {
        val accountLists: ArrayList<AccountList> = ArrayList<AccountList>(0)
        val newsArray = JSONArray(jsonString)
        var i = 0
        var numIterations = newsArray.length()
        while (i < numIterations) {
            val accountListsObject: JSONObject = newsArray.getJSONObject(i)
            val account_List = AccountList()
            account_List.accountType = accountListsObject.getString("accountType")
            account_List.accountNumber = accountListsObject.getString("accountNumber")
            account_List.balance = accountListsObject.getString("balance")
            account_List.id = accountListsObject.getInt("id")
            accountLists.add(account_List)
            i++
        }
        return accountLists
    }

    private fun readJsonFromKotlinFile(): String {
        var inputString = ""

        try {
            val inputStream: InputStream = context!!.assets.open("account_list.json")
            inputString = inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.d(HomeFragment.TAG, e.toString())
        }
        return inputString
    }


}
