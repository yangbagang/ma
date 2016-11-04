package com.ybg.yxym.ma

class RuiShareUser {

    static belongsTo = [share: RuiShare, userBase: UserBase]

    static constraints = {
    }

    Date createTime = new Date()
}
