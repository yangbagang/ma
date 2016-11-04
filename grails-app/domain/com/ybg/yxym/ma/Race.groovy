package com.ybg.yxym.ma

class Race {

    static constraints = {
        fromDate nullable: true
        toDate nullable: true
    }

    String title = ""//标题
    String summary = ""//简介
    Date createTime = new Date()
    Date fromDate
    Date toDate
    Short flag = 1 as Short//是否开放1开放，可以参与；0关闭，不能参与。
}
