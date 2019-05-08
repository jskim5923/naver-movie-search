package com.example.movie_search.base

import android.support.v7.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T, H : RecyclerView.ViewHolder>() : RecyclerView.Adapter<H>() {
    protected var dataList: List<T>? = null

    constructor(itemList: List<T>) : this() {
        this.dataList = ArrayList(itemList)
    }

    fun updateItem(itemList: List<T>) {
        this.dataList = ArrayList(itemList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }
}