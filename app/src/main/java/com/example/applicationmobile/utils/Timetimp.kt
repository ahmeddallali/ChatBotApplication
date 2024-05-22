package com.example.applicationmobile.utils
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object Timetimp {
    fun timeStamp(): String {

        val timeStamp  = Timestamp(System.currentTimeMillis())
        val sdf = SimpleDateFormat("HH:mm")
        val time = sdf.format(Date(timeStamp.time))

        return time.toString()
    }
}