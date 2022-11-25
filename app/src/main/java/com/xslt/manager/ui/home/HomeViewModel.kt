package com.xslt.manager.ui.home

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.xslt.manager.db.AppDatabase
import com.xslt.manager.db.dao.OrderDao
import com.xslt.manager.db.model.TyreInfoModel
import com.xslt.manager.db.dao.TyreInfoDao
import com.xslt.manager.db.model.OrderModel
import com.xslt.manager.ui.base.BaseViewModel
import com.xslt.manager.util.catchLog
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    var tyreInfoDao: TyreInfoDao? = null
    var orderDao: OrderDao? = null

    val tyreListInfo: MutableState<ArrayList<TyreInfoModel>> = mutableStateOf(ArrayList<TyreInfoModel>())
    val orderListInfo: MutableState<ArrayList<OrderModel>> = mutableStateOf(ArrayList<OrderModel>())

    fun initData(context: Context) {
        if (tyreInfoDao == null) tyreInfoDao = AppDatabase.getInstance(context).tyreInfoDao()
        if (orderDao == null) orderDao = AppDatabase.getInstance(context).orderDao()
        launchOnIO {
            launch {
                getOrderListData()
            }
            launch {
                getTyreListData()
            }
        }
    }

    fun getAllProfile():String{
        val profilt = orderListInfo.value.fold(0){ acc,order->
            acc + order.getProfit()
        }
        return "总盈利:${profilt}"
    }

    suspend fun getTyreListData() {
        tyreInfoDao!!
            .getAll()
            .catchLog()
            .collect {
                tyreListInfo.value = ArrayList(it)
            }
    }


    suspend fun getOrderListData() {
        orderDao!!
            .getAll()
            .catchLog()
            .collect {
                orderListInfo.value = ArrayList(it)
            }
    }


    fun addNewTyreInfo(tyreInfoModel: TyreInfoModel) {
        launchOnIO {
            val findResult = tyreListInfo.value.find { it.isSame(tyreInfoModel) }
            if (findResult != null) {
                findResult.count += tyreInfoModel.count
                tyreInfoDao?.updateTyreInfoModel(findResult)
            } else {
                tyreListInfo.value.add(tyreInfoModel)
                tyreInfoDao?.insert(tyreInfoModel)
            }
            showToast("添加成功！")
        }
    }

    fun updateTyreModel(tyreInfoModel: TyreInfoModel){
        launchOnIO {
            tyreInfoDao?.updateTyreInfoModel(tyreInfoModel)
            showToast("保存成功！")
        }
    }

    fun sellTyre(tyreInfoModel: TyreInfoModel, sellCount: Int, price: String) {
        launchOnIO {
            val findResult = tyreListInfo.value.find { it.isSame(tyreInfoModel) }
            if (findResult != null) {
                findResult.count -= sellCount
                tyreInfoDao?.updateTyreInfoModel(findResult)
            }
            createOrder(tyreInfoModel, sellCount, price)
            showToast("售卖成功！")
        }
    }

    fun createOrder(tyreInfoModel: TyreInfoModel, sellCount: Int, price: String) {
        launchOnIO {
            val orderModel = tyreInfoModel.createOrder(sellCount, price)
            orderListInfo.value.add(orderModel)
            orderDao?.insert(orderModel)
        }
    }

    fun getBrandList(): MutableState<List<String>> {
        val list = tyreListInfo.value
            .groupBy { it.brand }
            .keys.toList()
        return mutableStateOf(list)
    }

    fun getTyreList(brand: String): List<TyreInfoModel> {
        val list = tyreListInfo.value
            .groupBy { it.brand }[brand]
        return list ?: emptyList()
    }


}