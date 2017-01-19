package com.ybg.yxym.ma

class ShowViewHistory {

    static belongsTo = [show: RuiShow, userBase: UserBase]

    static constraints = {
    }

    Date createTime = new Date()//观看时间
}
