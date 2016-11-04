package com.ybg.yxym.ma

class RuiGift {

    static constraints = {
        name unique: true
    }

    Float basePrice = 0//价格，多少美票
    Float realPrice = 0//实际出售价格
    Float buyPrice = 0//回收价格
    String name
    String image = ""//图片
    Integer rq = 0//人气值
    Integer hl = 0//活动值
    Integer qs = 0//亲善值
    Short flag = 1 as Short//是否在售 1在售 0下架
}
