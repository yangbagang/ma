package com.ybg.yxym.ma

class GroupMember {

    static belongsTo = [group: UserGroup]

    static constraints = {
    }

    UserBase member
    Date joinTime
    Integer role = 0
}
