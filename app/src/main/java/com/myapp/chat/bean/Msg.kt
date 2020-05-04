package com.myapp.chat.bean

class Msg(val msg:String,val type:Int) {
    companion object{
        const val MSG_RECEIVED=0
        const val MSG_SEND=1
    }
}