package com.example.myapplication.fragment.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_notification_detail.*

/**
 * A simple [Fragment] subclass.
 * Use the [NotificationDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationDetailFragment : Fragment() {

    private var headerText: String? = null
    private var subjectText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        headerText = arguments?.getString("subject")
        subjectText = arguments?.getString("body")
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notificationDetailViewHeader.text = headerText
        notificationDetailViewTxtView.text = subjectText
    }
}
