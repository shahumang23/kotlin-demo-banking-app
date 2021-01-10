package com.example.myapplication.fragment.account

import TransactionList
import Transactions
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.AccountSelectionAdapter
import com.example.myapplication.adapter.TransactionListRecyclerAdapter
import com.example.myapplication.fragment.transfer.TransferFragment
import com.example.myapplication.listener.OnFragmentInteractionListener
import com.example.myapplication.model.AccountList
import kotlinx.android.synthetic.main.fragment_account.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import kotlin.collections.ArrayList

class AccountFragment: Fragment() {
    lateinit var adapter: TransactionListRecyclerAdapter
    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("Fragment","Transfer Fragment")
        val view =  inflater.inflate(R.layout.fragment_account, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = "Account Services"

        var transferList = view.findViewById(R.id.transferRecyclerView) as RecyclerView
        var spinner = view.findViewById(R.id.accountSelectedDropDown) as Spinner
        val accountListJsonString:String = readJsonFromKotlinFile("account_list.json")
        val accountSelectionList: ArrayList<AccountList> = parseAccountListJsonStringToList(accountListJsonString)
        val accountSelecterAdapter = AccountSelectionAdapter(this.context, R.layout.account_selection_item, accountSelectionList)
        spinner.setAdapter(accountSelecterAdapter)
        adapter = TransactionListRecyclerAdapter()
        val jsonString:String = readJsonFromKotlinFile("transaction_list.json")
        val transactionList : ArrayList<TransactionList> = parseJsonStringToList(jsonString)
        populateListItem(transactionList)

        transferList.transferRecyclerView.layoutManager = LinearLayoutManager(this.context)
        transferList.transferRecyclerView.adapter = adapter
        return view
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

    companion object {
        val TAG = "Transfer Fragment"
    }

    private fun populateListItem(transactionList:ArrayList<TransactionList>) {
        Log.d("TranscationList", transactionList.toString())
        adapter.setTransactionList(transactionList)
    }

    private fun parseAccountListJsonStringToList(jsonString: String): ArrayList<AccountList> {
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

    private fun parseJsonStringToList(jsonString: String): ArrayList<TransactionList> {
        val transcationList: ArrayList<TransactionList> = ArrayList<TransactionList>(0)
        val jsonArray = JSONObject(jsonString).getJSONArray("transactionList")
        var numIterations = jsonArray.length()
        for (i in 0 until numIterations) {
            val transcationListsObject: JSONObject = jsonArray.getJSONObject(i)
            val transcationDate = transcationListsObject.getString("date")
            val transcationDetailList = transcationListsObject.getJSONArray("transactions")
            val transcationsDetailList: ArrayList<Transactions> = ArrayList<Transactions>(0)
            for (j in 0 until transcationDetailList.length()) {
                val transcationDetailObject: JSONObject = transcationDetailList.getJSONObject(j)
                val id = transcationDetailObject.getInt("id")
                val vendorName = transcationDetailObject.getString("vendorName")
                val vendor = transcationDetailObject.getString("vendor")
                val amount = transcationDetailObject.getString("amount")
                transcationsDetailList.add(Transactions(id, vendor, vendorName, amount ))
            }
            transcationList.add(TransactionList(transcationDate, transcationsDetailList))
        }
        return transcationList
    }

    private fun readJsonFromKotlinFile(assetsName: String): String {
        var inputString = ""

        try {
            val inputStream: InputStream = context!!.assets.open( assetsName)
            inputString = inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.d(TransferFragment.TAG, e.toString())
        }
        return inputString
    }
}