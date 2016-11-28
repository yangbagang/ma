package com.ybg.yxym.ma

class MeiLiHistory {

    static belongsTo = [userBase: UserBase]

    static constraints = {
    }

    Date createTime = new Date()
    Short type//类型1=人气值， 2=活动值， 3=亲善值
    Integer score = 0//得分
    String reason = ""//原因
    Long showId = 0//指向美秀ID

    static createInstance(UserBase userBase, Short type, Integer score, String reason, Long showId) {
        def meiLiHistory = new MeiLiHistory()
        meiLiHistory.userBase = userBase
        meiLiHistory.type = type
        meiLiHistory.score = score
        meiLiHistory.reason = reason
        meiLiHistory.showId = showId
        meiLiHistory.save flush: true
        meiLiHistory
    }
}
