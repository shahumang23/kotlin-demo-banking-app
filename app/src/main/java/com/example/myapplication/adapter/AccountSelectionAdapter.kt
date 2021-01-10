package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.model.AccountList

/**
 * Created by Umang Shah on 29/04/20.
 * shahumang8@gmail.com
 */
class AccountSelectionAdapter(private val mContext: Context?,
                              private val viewResourceId: Int,
                              private val items: ArrayList<AccountList>): BaseAdapter() {

    private val itemsAll = items.clone() as ArrayList<AccountList>

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var v: View? = convertView
        if (v == null) {
            val vi = mContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v = vi.inflate(viewResourceId, null)
        }
        val accountListDetails: AccountList? = items[position]
        if (accountListDetails != null) {
            val accountType = v?.findViewById(R.id.accountType) as TextView?
            val accountNumber = v?.findViewById(R.id.accountNumberView) as TextView?
            accountType?.text = accountListDetails.accountType
            accountNumber?.text = accountListDetails.accountNumber
        }
        return v!!
    }

    override fun getItem(position: Int): Any? {
       return null
    }

    override fun getItemId(position: Int): Long {
        return 0;
    }

    override fun getCount(): Int {
        return items.size
    }
}