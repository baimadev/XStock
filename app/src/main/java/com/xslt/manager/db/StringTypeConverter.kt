package com.xslt.manager.db

import androidx.room.TypeConverter
import com.xslt.manager.util.MoshiUtils
import com.xslt.manager.util.TypeToken
import java.lang.reflect.Type

class StringTypeConverter {

    @TypeConverter
    fun stringToStringList(str: String?): List<String>? {
        if(str == null)return emptyList()
        val listType: Type = object : TypeToken<List<String>>() {}.type

        return MoshiUtils.fromJson(str, listType)
    }

    @TypeConverter
    fun stringListToString(list:List<String>): String {
        return MoshiUtils.toJson(list)
    }
}