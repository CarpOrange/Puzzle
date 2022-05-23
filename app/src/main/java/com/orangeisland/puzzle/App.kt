package com.orangeisland.puzzle

import android.app.Application

/**
 *
 *
 * @author 李昱辰 987424501@qq.com
 * @date 2022/5/23
 */
class App :Application() {
   companion object {
       lateinit var instance: App
   }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}