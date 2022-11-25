package com.xslt.manager

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import com.xslt.manager.db.AppDatabase
import com.xslt.manager.util.XPrefs

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        _context = this
        initXPrefs()
        AppDatabase.getInstance(this)
    }

    /**初始化xprefs*/
    private fun initXPrefs() {
        XPrefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }

    companion object {
        var _context: Application? = null
        fun getContext(): Context {
            return _context!!
        }

        fun getApplication(): Application {
            return _context!!
        }
    }
}