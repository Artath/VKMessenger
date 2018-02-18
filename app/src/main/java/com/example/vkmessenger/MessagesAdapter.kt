package com.example.vkmessenger

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.vk.sdk.api.model.VKApiDialog
import com.vk.sdk.api.model.VKApiMessage
import com.vk.sdk.api.model.VKList
import kotlinx.android.synthetic.main.message_item.view.*
import android.R.attr.button
import android.R.attr.gravity
import android.view.ViewGroup.LayoutParams.FILL_PARENT
import android.widget.LinearLayout
import android.R.attr.button
import android.view.ViewGroup.LayoutParams.FILL_PARENT




/**
 * Created by Дом on 17.02.2018.
 */
class MessagesAdapter(var data: ArrayList<VKApiMessage>): RecyclerView.Adapter<MessagesAdapter.ViewHolder>(){

    interface AdapterCallback {
        fun onClick(id: String)
    }

    lateinit var callBack: AdapterCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (data[position].out){
            holder.viewGroup.gravity = Gravity.END
        }else{
            holder.viewGroup.gravity = Gravity.START
        }

        holder.messageTxtView.text = data[position].body


    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var messageTxtView = view.messageTxtView
        var viewGroup = view.viewGroup
    }


}