package com.xslt.manager.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.xslt.manager.ui.home.screen.safeToFloat

@Entity
data class TyreInfoModel(
    @PrimaryKey var gid: Long = System.currentTimeMillis(),
    val brand: String,

    //轮胎宽度 215
    val width: String,
    //扁平比 70
    val flatRatio: String,
    //轮毂尺寸 15
    val hub: String,

    var count: Int,
    val other: String = "",
    //进价
    var inPrice: Int,
    //卖价
    var outPrice: Int,
) {
    fun getType(): String {
        return "${width}/${flatRatio}R${hub}"
    }

    fun isSame(tyreInfoModel: TyreInfoModel): Boolean {
        if (this.getType() == tyreInfoModel.getType()) {
            if (brand == tyreInfoModel.brand) {
                return true
            }
        }
        return false
    }

    fun createOrder(sellCount:Int,sellPrice:String):OrderModel{
        return OrderModel(
            brand = this.brand,
            type = getType(),
            sellTime = System.currentTimeMillis(),
            count = sellCount,
            inPrice = this.inPrice,
            outPrice = safeToFloat(sellPrice),
        )
    }

    companion object {
        val defaultBrandList = arrayOf("佳通", "邓禄普", "米其林")
        val defaultWidthList = arrayOf(
            "145",
            "155",
            "165",
            "175",
            "185",
            "195",
            "205",
            "215",
            "225",
            "235",
            "245",
            "255",
            "265",
            "275",
            "285"
        )
        val defaultFlatRatioList = arrayOf("40", "45", "50", "55", "60", "65", "70", "75")
        val defaultHubList = arrayOf("12", "13", "14", "15", "16", "17", "18", "19")
        val defaultCountList = Array(50) { it ->
            it.toString()
        }
    }
}
