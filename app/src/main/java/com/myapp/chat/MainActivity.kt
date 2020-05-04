package com.myapp.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.chat.adapter.MsgAdapter
import com.myapp.chat.bean.Msg
import com.myapp.chat.util.HttpUtil
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity(),View.OnClickListener,Callback {
    private val msgList=ArrayList<Msg>()
    private var adapter:MsgAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initList()
        val layoutManager= LinearLayoutManager(this)
        chatRecyclerview.layoutManager=layoutManager
        adapter=MsgAdapter(msgList)
        chatRecyclerview.adapter=adapter
        senMsg.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view){
            senMsg->{
                val content=msgText.text.toString()
                if (content.isNotEmpty()){
                    val msg=Msg(content,Msg.MSG_SEND)
                    handMsg(content)
                    msgList.add(msg)
                    adapter?.notifyItemInserted(msgList.size-1)
                    chatRecyclerview.scrollToPosition(msgList.size-1)
                    msgText.setText("")
                }
            }
        }
    }
    private fun initList(){
        msgList.add(Msg("我是你的AI助手",Msg.MSG_RECEIVED))
        msgList.add(Msg("请向我提问吧",Msg.MSG_RECEIVED))
    }
    private fun handMsg(msg: String){
        HttpUtil.robotHttpRequest(msg,this)
    }
    override fun onFailure(call: Call, e: IOException) {
        msgList.add(Msg("error",Msg.MSG_RECEIVED))
        adapter?.notifyItemInserted(msgList.size-1)
    }
    override fun onResponse(call: Call, response: Response) {
        response.body?.let {
            val receivedMsg=HttpUtil.handRobotMsg(it.string())
            msgList.add(Msg(receivedMsg,Msg.MSG_RECEIVED))
            runOnUiThread { adapter?.notifyItemInserted(msgList.size-1)
                chatRecyclerview.scrollToPosition(msgList.size-1) }
        }
        }


}
