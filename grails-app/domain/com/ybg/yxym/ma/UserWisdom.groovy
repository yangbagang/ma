package com.ybg.yxym.ma

class UserWisdom {

    static belongsTo = [userBase: UserBase]

    static constraints = {
    }

    UserBase relatives
    Date createTime
}
