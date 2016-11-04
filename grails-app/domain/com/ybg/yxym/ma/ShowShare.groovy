package com.ybg.yxym.ma

class ShowShare {

    static belongsTo = [show: RuiShow]

    static constraints = {
    }

    Date createTime = new Date()
    UserBase userBase

}
