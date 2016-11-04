package com.ybg.yxym.ma

class ShowPing {

    static belongsTo = [show: RuiShow]

    static constraints = {
    }

    Date createTime = new Date()
    UserBase userBase
    String ping = ""//评论内容
}
