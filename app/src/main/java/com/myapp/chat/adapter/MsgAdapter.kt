package com.myapp.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myapp.chat.R
import com.myapp.chat.bean.Msg


class MsgAdapter(var msgList: List<Msg>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    inner class leftViewHolder(view: View):RecyclerView.ViewHolder(view){
        val leftMsg:TextView=view.findViewById(R.id.leftMsg)
    }
    inner class rightViewHolder(view: View):RecyclerView.ViewHolder(view){
        val rightMsg:TextView=view.findViewById(R.id.rightMsg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=when(viewType){
        Msg.MSG_RECEIVED ->{
            val view=LayoutInflater.from(parent.context).inflate(R.layout.activity_msgitem_left,parent,false)
            leftViewHolder(view)
        }
        Msg.MSG_SEND ->{
            val view=LayoutInflater.from(parent.context).inflate(R.layout.activity_msgitem_right,parent,false)
            rightViewHolder(view)
        }
        else ->throw  IllegalArgumentException()

    }

    override fun getItemCount()=msgList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg=msgList[position]
        when(holder){
            is leftViewHolder-> holder.leftMsg.text=msg.msg
            is rightViewHolder ->holder.rightMsg.text=msg.msg
            else->throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int)=msgList[position].type
}