package com.example.photo

import android.app.Application
import android.content.Context

class GlobalApplication: Application() {
    init {
        instance = this
    }
    companion object {
        private lateinit var instance: GlobalApplication
        fun getInstance() : Context {
            return instance.applicationContext
        }
    }
}