package com.ybg.yxym.ma

class Follow {

    static belongsTo = [userBase: UserBase]

    static constraints = {
    }

    Date createTime = new Date()
    UserBase follow
}
