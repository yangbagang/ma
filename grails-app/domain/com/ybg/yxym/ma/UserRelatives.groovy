package com.ybg.yxym.ma

class UserRelatives {

    static belongsTo = [userBase: UserBase]

    static constraints = {
    }

    UserBase relatives
    Date createTime
}
