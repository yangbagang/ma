package com.ybg.yxym.ma

class UserDate {

    static belongsTo = [userBase: UserBase]

    static constraints = {
    }

    UserBase fromUser
    Date createTime = new Date()
    Date dateDate = new Date()
    String type = ""
    String content = ""
    Integer flag = 0//0未处理，1己接受，2己拒绝，3忽略

}
