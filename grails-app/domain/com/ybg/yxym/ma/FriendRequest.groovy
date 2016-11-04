package com.ybg.yxym.ma

class FriendRequest {

    static constraints = {
    }

    UserBase fromUser
    UserBase targetUser
    Date createTime
    String reason = ""
    Short status = 0 as Short//状态 0申请中 1同意 2拒绝
}
