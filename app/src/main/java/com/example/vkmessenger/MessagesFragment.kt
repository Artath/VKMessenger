package com.example.vkmessenger


import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.vk.sdk.api.*
import com.vk.sdk.api.model.VKApiMessage
import kotlinx.android.synthetic.main.fragment_messages.*
import org.json.JSONArray
import org.json.JSONException
import java.lang.reflect.Array


/**
 * A simple [Fragment] subclass.
 */
class MessagesFragment : Fragment() {

    var userId:Int = 0

    companion object {
        val PARAM_KEY = "str"
        fun newInstance(id: String): MessagesFragment {
            var bundl = Bundle()
            bundl.putString(PARAM_KEY, id)
            var result = MessagesFragment()
            result.arguments = bundl
            return result
        }
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        userId = if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            if (arguments.getString(PARAM_KEY) !== null) {
                arguments.getString(PARAM_KEY).toInt()
            }else{
                0
            }
        }else{
            activity.intent.getStringExtra("USER_ID").toInt()
        }




        sendBtn.setOnClickListener(View.OnClickListener {
            if(messagesFragmentEditTxt.text.isNotEmpty()){
            var requaest = VKRequest("messages.send", VKParameters.from(VKApiConst.USER_ID, userId, VKApiConst.MESSAGE, messagesFragmentEditTxt.text))
            requaest.executeWithListener(object: VKRequest.VKRequestListener(){
                override fun onComplete(response: VKResponse?) {
                    super.onComplete(response)
                    populate()
                    messagesFragmentEditTxt.setText("")

                }
            })}
        })

        populate()


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    fun populate(){
        var request = VKRequest("messages.getHistory", VKParameters.from(VKApiConst.USER_ID, userId))
        request.executeWithListener(object: VKRequest.VKRequestListener(){
            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)

                try {
                    var array: JSONArray = response!!.json.getJSONObject("response").getJSONArray("items")
                    var msg = ArrayList<VKApiMessage>()

                    (0..(array.length() - 1)).mapTo(msg) { VKApiMessage(array.getJSONObject(it)) }

                    msg.reverse()


                    var layoutManager = LinearLayoutManager(activity.applicationContext)
                    layoutManager.scrollToPosition(msg.size - 1)
                    messagesFragmentRecyclerView.layoutManager = layoutManager
                    messagesFragmentRecyclerView.adapter = MessagesAdapter(msg)




                }catch (e: JSONException){
                    e.printStackTrace()
                }

            }
        })
    }

}// Required empty public constructor
