package com.example.vkmessenger


import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vk.sdk.api.*
import kotlinx.android.synthetic.main.fragment_dialogs.*
import com.vk.sdk.api.VKApiConst
import com.vk.sdk.api.VKParameters
import com.vk.sdk.api.VKApi
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.methods.VKApiFriends
import com.vk.sdk.api.model.*


/**
 * A simple [Fragment] subclass.
 */
class DialogsFragment : Fragment() {


    var usersIds = ArrayList<Int>()
    var usersNames = ArrayList<String>()
    var lastMessages = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialogs, container, false)


    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val request = VKApi.messages().getDialogs(VKParameters.from(VKApiConst.COUNT, 50, VKApiConst.PREVIEW_LENGTH, 30))
        request.executeSyncWithListener(object: VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)

                var list = (response!!.parsedModel as VKApiGetDialogResponse).items

                for(msg:VKApiDialog in list){
                    usersIds.add(msg.message.user_id)
                    lastMessages.add(msg.message.body)
                }
            }
        })


        val namesRequest = VKApi.users().get(VKParameters.from(VKApiConst.USER_IDS, usersIds))
        namesRequest.executeSyncWithListener(object: VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)

                for(i: Int in usersIds) {
                    var firstName = (response!!.parsedModel as VKList<VKApiUserFull>).getById(i).first_name
                    var lastName = (response!!.parsedModel as VKList<VKApiUserFull>).getById(i).last_name
                    var name = firstName +" "+ lastName
                    usersNames.add(name)
                }
            }
        })



        dialogFragmentRecyclerView.layoutManager = LinearLayoutManager(activity.applicationContext)
        var adapter = DialogsAdapter(activity.applicationContext, usersNames, lastMessages, usersIds)
        adapter.callBack = activity as DialogsAdapter.AdapterCallback
        dialogFragmentRecyclerView.adapter = adapter
        dialogFragmentRecyclerView.adapter.notifyDataSetChanged()








    }
}// Required empty public constructor
