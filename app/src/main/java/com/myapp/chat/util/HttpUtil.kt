package com.myapp.chat.util

import android.util.Log
import com.google.gson.Gson
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject

class HttpUtil {
    companion object{
        const val robotUrl="https://api.ownthink.com/bot?appid=eea1b2a161c267f2deb082ebbdab8b3e&userid=cf0f3d43b6f448b885e218759a1ebcc9&spoken="
        fun robotHttpRequest(msg:String,callback: Callback){
            val address= robotUrl+msg
            Log.d("exam",address)

            val client=OkHttpClient()
            val request=Request.Builder().url(address).build()
            client.newCall(request).enqueue(callback)
        }
        fun handRobotMsg(msg: String):String{
            try {
                val jsondata=JSONObject(msg)
                val data=jsondata.getString("data")
                val info=JSONObject(data)
                val text=info.getString("info")
                val msgJson=JSONObject(text)
                val msgInfo=msgJson.getString("text")
                return msgInfo
            }catch (e:JSONException){e.printStackTrace()
            }
            return "error"
        }

    }
}