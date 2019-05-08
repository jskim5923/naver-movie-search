package com.example.movie_search.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity<T: ViewDataBinding>:  AppCompatActivity(){

    lateinit var dataBinding: T

    abstract val layoutResourceId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, layoutResourceId)
    }
}