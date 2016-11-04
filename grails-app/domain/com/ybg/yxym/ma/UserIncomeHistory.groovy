package com.ybg.yxym.ma

class UserIncomeHistory {

    static belongsTo = [userBase: UserBase]

    static constraints = {
        reasonCode nullable: true
        reasonMsg nullable: true
    }

    Integer reasonCode = 1//产生代码
    String reasonMsg = ""//理由
    Date createTime = new Date()//时间
    Float money = 0//数量
}
