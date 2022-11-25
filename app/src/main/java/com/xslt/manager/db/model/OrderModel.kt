package com.xslt.manager.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrderModel(
    @PrimaryKey var gid: Long = System.currentTimeMillis(),
    //品牌
    val brand: String,
    //215/55/R15
    val type:String,
    //卖出数量
    var count: Int,
    //进价
    var inPrice: Int,
    //实际卖价
    var outPrice: Int,
    //卖出时间
    val sellTime:Long = System.currentTimeMillis()
) {

    fun getProfit():Int{
        return (outPrice - inPrice)*count
    }

}
