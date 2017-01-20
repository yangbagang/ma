package com.ybg.yxym.ma

import com.ybg.yxym.utils.Base64Util
import grails.transaction.Transactional

class VideoInfoService {

    def importAt() {
        //def maxId = 9124
        def maxNum = 237
        def urlBase = "http://www.avtb66.com/recent/"
        def uriList = []
        for (def i = 1; i <= maxNum; i++) {
            println("获取第${i}页")
            //获取地址
            uriList = getAtHtmlUri(urlBase, i)
            if (uriList != null && !uriList.empty) {
                uriList.each {
                    println("获取内容：${it}")
                    try {
                        parseAtInfo("http://www.avtb66.com" + it)
                    } catch (Exception e) {
                        println("获取内容失败：${it}")
                        println e.message
                        e.printStackTrace()
                    }
                }
            } else {
                println("获取第${i}页失败")
            }
        }
    }

    def getAtHtmlUri(String urlBase, Integer i) {
        try {
            def url = i == 1 ? urlBase : urlBase + i
            def html = url.toURL().text
            //解析
            def lines = html.split("\n")
            def list = []
            def pattern = /.*<a href="(.+)" title="(.+)" class="thumbnail" target="_blank">.*/
            lines.each { line ->
                def matcher = line =~ pattern
                if (matcher.matches()) {
    //                def vo = [:]
    //                vo.href = matcher[0][1]
    //                vo.title = matcher[0][2]
                    list.add(matcher[0][1])
                }
            }
            return list
        } catch (Exception e) {
            return null
        }
    }

    @Transactional
    def parseAtInfo(String url) {
        //准备
        def videoInfo = new VideoInfo()
        def html = url.toURL().text
        //解析
        def lines = html.split("\n")
        def videoUrl = ""
        def titleInfo = ""
        def imgInfo = ""
        def videoId = ""
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
            //获取缩略图地址
            matcher = line =~ /.*meta property="og:image" content="(.+)".*/
            if (matcher.matches()) {
                imgInfo = matcher[0][1]
            }
            //获取视频序号
            matcher = line =~ /.*input name="video_id" type="hidden" value="(.+)".*/
            if (matcher.matches()) {
                videoId = matcher[0][1]
            }
        }
        println("title=${titleInfo}")
        println("video=${videoUrl}")
        //构建结果集
        videoInfo.infoKey = "at"
        videoInfo.urlInfo = videoUrl
        videoInfo.titleInfo = Base64Util.getEncodeString(titleInfo)
        videoInfo.imgInfo = imgInfo
        try {
            videoInfo.videoId = Integer.valueOf(videoId)
        } catch (Exception e) {
            videoInfo.videoId = 0
            e.printStackTrace()
        }
        videoInfo.save flush: true
    }

    def updateAt(String oldString, String newString) {
        VideoInfo.findAllByInfoKey("at").each { video ->
            video.urlInfo = video.urlInfo.replaceAll(oldString, newString)
            video.imgInfo = video.imgInfo.replaceAll(oldString, newString)
            video.save flush: true
        }
    }
}
