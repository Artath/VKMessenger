package com.example.vkmessenger

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.util.VKUtil
import kotlinx.android.synthetic.main.activity_main.*
import com.vk.sdk.VKSdk.onActivityResult
import com.vk.sdk.api.VKError
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import java.util.Arrays.asList


class MainActivity : AppCompatActivity(), DialogsAdapter.AdapterCallback {
    override fun onClick(id: String) {

        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, MessagesFragment.newInstance(id))
                    .commit()
        }else{
            val intent = Intent(this, MessagesActivity::class.java)
            intent.putExtra("USER_ID", id.toString())
            startActivity(intent)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        if(VKSdk.wakeUpSession(applicationContext)){

        }else{
            VKSdk.login(this, VKScope.MESSAGES, VKScope.FRIENDS)
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (!// Пользователь успешно авторизовался
        // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
        VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
            override fun onResult(res: VKAccessToken) {

            }
            override fun onError(error: VKError) {System.exit(0)}
        })) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
