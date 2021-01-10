package com.example.myapplication.model.notification

/**
 * Created by Umang Shah on 25/05/20.
 * shahumang8@gmail.com
 */

data class NotificationList (

    val id : Int,
    val body : String,
    val subject : String,
    val dateTime : String,
    val readableStatus : Boolean
)