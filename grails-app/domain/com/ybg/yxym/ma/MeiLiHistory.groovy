package com.ybg.yxym.ma

class MeiLiHistory {

    static belongsTo = [userBase: UserBase]

    static constraints = {
    }

    Date createTime = new Date()
    Short type//类型1=人气值， 2=活动值， 3=亲善值
    Integer score = 0//得分
    String reason = ""//原因
}
