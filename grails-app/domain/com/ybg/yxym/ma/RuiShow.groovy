package com.ybg.yxym.ma

class RuiShow {

    static belongsTo = [userBase: UserBase]

    static constraints = {
        thumbnail nullable: true
        title nullable: true
        ruiEvent nullable: true
        ruiBar nullable: true
    }

    String thumbnail = ""//缩略图
    String title = ""//说明
    Date createTime = new Date()//发布时间
    Integer viewNum = 0//查看次数
    Integer pingNum = 0//评论次数
    Integer zanNum = 0//赞次数
    Integer shareNum = 0//分享次数
    RuiEvent ruiEvent
    RuiBar ruiBar
    Short flag = 1 as Short//标志，1可用，0不可用

    transient List<ShowFile> files

}
