package com.ybg.yxym.ma

class RaceUser {

    static belongsTo = [race: Race]

    static constraints = {
    }

    Date createTime = new Date()
    UserBase userBase
}
