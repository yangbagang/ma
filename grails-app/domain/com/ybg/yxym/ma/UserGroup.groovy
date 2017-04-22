package com.ybg.yxym.ma

class UserGroup {

    static belongsTo = [userBase: UserBase]

    static constraints = {
    }

    String name
    Date createTime = new Date()
    Integer level = 1
    Long groupId = 0L
}
