package com.ybg.yxym.ma

class UserLabel {

    static belongsTo = [userBase: UserBase, systemLabel: SystemLabel]

    static constraints = {
    }
}
