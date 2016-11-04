package com.ybg.yxym.ma

class UserGift {

    static belongsTo = [userBase: UserBase, gift: RuiGift]

    static constraints = {
        updateTime nullable: true
        targetUser nullable: true
    }

    Date createTime
    Date updateTime
    Short flag = 1 as Short//是否还在 1还在，可用；0不在，不可用，如己送人。
    Short direction = 1 as Short//方向 1收到，0送出, 2系统回收
    UserBase targetUser//目标
}
