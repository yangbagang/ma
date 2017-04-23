package com.ybg.yxym.ma

class RuiGift {

    static constraints = {
        name unique: true
    }

    Integer basePrice = 0//价格，多少美票
    Integer realPrice = 0//实际出售价格
    String name = ""
    String unitName = ""
    String image = ""//图片
    Integer rq = 0//人气值
    Integer hl = 0//活动值
    Integer qs = 0//亲善值
    Short flag = 1 as Short//是否在售 1在售 0下架
}
