package com.ybg.yxym.ma

class GroupMember {

    static belongsTo = [group: UserGroup]

    static constraints = {
    }

    UserBase member
    String nickName = member?.nickName
    Date joinTime = new Date()
    Integer role = 0
}
