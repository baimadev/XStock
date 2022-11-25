package com.xslt.manager.ui.base

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


/**
 * @ClassName : BaseViewModel
 * @Description:
 * @Author: WuZhuoyu
 * @Date: 2021/3/9 12:17
 */

abstract class BaseViewModel: ViewModel() {


    @SuppressLint("StaticFieldLeak")
    lateinit var application: Application

    lateinit var lifcycleOwner: LifecycleOwner

    /**
     * loading视图显示Event
     * */
    var loadingEvent = MutableLiveData<Boolean>()
        private set

    /**
     *  无数据视图显示Event
     * */
    var emptyPageEvent = MutableLiveData<Boolean>()
        private set

    /**
     *  网络错误视图显示Event
     * */
    var errorNetworkPageEvent = MutableLiveData<Boolean>()
        private set

    /**
     * toast提示Event
     * */
    var toastEvent = MutableLiveData<String>()
        private set

    /**
     * 在主线程中执行一个协程
     */
    protected fun launchOnUI(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(Dispatchers.Main) { block() }
    }

    /**
     * 在IO线程中执行一个协程
     */
    protected fun launchOnIO(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
            } catch (e: Exception) {
                Log.e("xia", e.toString())
            }
        }
    }

    /**
     * 通知是否展示Toast
     * @param msg
     * */
    fun showToast(msg: String) {
        toastEvent.postValue(msg)
    }
//
//    fun <T> Flow<T>.loading(): Flow<T> {
//        return this.onStart {
//            showLoadingUI(true)
//        }.onCompletion {
//            showLoadingUI(false)
//        }
//    }
//
//    fun <T> Flow<T>.catch(): Flow<T> {
//        return this.catch {
//            showToast(it.message ?: it.toString())
//            Log.e("flow catch","BaseViewModel ${it.message ?: it.toString()}")
//        }
//    }
}