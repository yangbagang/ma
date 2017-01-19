package com.ybg.yxym.ma

import grails.transaction.Transactional

@Transactional
class VideoInfoService {

    def importAt() {
        def maxId = 9124
        def urlBase = "http://www.avtb66.com/%d/"
        for (def i = maxId; i > 0; i--) {
            parseAtInfo(urlBase, i)
        }
    }

    def parseAtInfo(String urlBase, Integer i) {
        //准备
        def url = String.format(urlBase, i)
        def videoInfo = new VideoInfo()
        def html = url.toURL().text
        //解析
        def lines = html.split("\n")
        def videoUrl = ""
        def titleInfo = ""
        lines.each { line ->
            //获取标题信息
            def matcher = line =~ /.*meta property="og:title" content="(.+)".*/
            if (matcher.matches()) {
                titleInfo = matcher[0][1]
            }
            //获取视频地址
            matcher = line =~ /.*source src="(.+)" type="video\/mp4" label="360p" res="360" .*/
            if (matcher.matches()) {
                videoUrl = matcher[0][1]
            }
        }
        //构建结果集
        videoInfo.key = "at"
        videoInfo.url = videoUrl
        videoInfo.title = titleInfo
        videoInfo.videoId = i
        videoInfo.save flush: true
    }
}
