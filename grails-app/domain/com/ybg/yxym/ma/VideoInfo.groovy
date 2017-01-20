package com.ybg.yxym.ma

class VideoInfo {

    static constraints = {
        infoKey nullable: true
        videoId nullable: true
        titleInfo nullable: true
        urlInfo nullable: true
        imgInfo nullable: true
    }

    String infoKey = ""
    Integer videoId = 0
    String titleInfo = ""
    String urlInfo = ""
    String imgInfo = ""
}
