package com.ybg.yxym.ma

class ShowZan {

    static belongsTo = [show: RuiShow]

    static constraints = {
    }

    Date createTime = new Date()
    UserBase userBase

}
