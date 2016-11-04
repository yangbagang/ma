package com.ybg.yxym.ma

class RuiShare {

    static belongsTo = [userBase: UserBase]

    static constraints = {
    }

    String subject = ""//主题
    Date date//时间
    String address//地点
    Integer num//人数
    Short obj//0不限1限男性2限女性
    Integer type//类型1发起人买单2AA
    String mem//描述
    Integer money//诚意金
    Short status//状态0进行中1己结束2己中止
}
