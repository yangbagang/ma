package com.ybg.yxym.ma

class Friend {

    static belongsTo = [userBase: UserBase]

    static constraints = {
    }

    UserBase friend
    Date createTime = new Date()
    String nickName = friend.nickName
    Short disturbing = 1 as Short
    Short inBlacklist = 0 as Short
}
