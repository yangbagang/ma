package com.ybg.yxym.ma

class UserAchievement {

    static belongsTo = [userBase: UserBase, achievement: SystemAchievement]

    static constraints = {
    }
}
