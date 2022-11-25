package com.xslt.manager.util

import java.text.SimpleDateFormat
import java.util.*


fun formatTime(time:Long):String{
    val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.CHINA)
    return dateFormat.format(time)
}