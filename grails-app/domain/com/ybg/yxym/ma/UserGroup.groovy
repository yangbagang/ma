package com.ybg.yxym.ma

class UserGroup {

    static belongsTo = [userBase: UserBase]

    static constraints = {
    }

    String name
    Date createTime
    Integer level = 1
}
