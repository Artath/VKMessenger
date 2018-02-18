package com.example.vkmessenger

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.vk.sdk.api.model.VKApiDialog
import com.vk.sdk.api.model.VKApiMessage
import com.vk.sdk.api.model.VKList
import kotlinx.android.synthetic.main.dialog_item.view.*

/**
 * Created by Дом on 17.02.2018.
 */
class DialogsAdapter(var contx: Context, var names: ArrayList<String>, var lastMessage: ArrayList<String>, var ids: ArrayList<Int>): RecyclerView.Adapter<DialogsAdapter.ViewHolder>(){

    interface AdapterCallback {
        fun onClick(id: String)
    }

    lateinit var callBack: AdapterCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dialog_item, parent, false))

    override fun getItemCount() = lastMessage.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        //callBack = activity.a

        holder.itemView.setOnClickListener(View.OnClickListener {
            Toast.makeText(contx, ids[position].toString(), Toast.LENGTH_SHORT).show()
            callBack.onClick(ids[position].toString())
        })

        holder.userName.text = names[position]
        holder.lastMessage.text = lastMessage[position]

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var userName = view.userName
        var lastMessage = view.lastMessge
    }


}