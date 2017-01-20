package com.ybg.yxym.ma

import com.ybg.yxym.utils.VideoUtil
import grails.converters.JSON

class VideoInfoController {

    def videoInfoService

    def importAt() {
        def key = "at"
        def map = [:]
        if (VideoUtil.checkIsRunning(key)) {
            map.isSuccess = false
            map.errorCode = "1"
            map.errorMsg = "正在运行中"
            map.data = ""
        } else {
            if (VideoUtil.addRunningKey(key)) {
                Thread.start {
                    videoInfoService.importAt()
                }
                map.isSuccess = false
                map.errorCode = "0"
                map.errorMsg = "开始运行组件"
                map.data = ""
            } else {
                map.isSuccess = false
                map.errorCode = "2"
                map.errorMsg = "添加运行组件失败"
                map.data = ""
            }
        }
        render map as JSON
    }

    def updateAt(String oldString, String newString) {
        def key = "at"
        def map = [:]
        if (VideoUtil.checkIsRunning(key)) {
            map.isSuccess = false
            map.errorCode = "1"
            map.errorMsg = "正在运行中"
            map.data = ""
        } else {
            if (VideoUtil.addRunningKey(key)) {
                Thread.start {
                    videoInfoService.updateAt(oldString, newString)
                }
                map.isSuccess = false
                map.errorCode = "0"
                map.errorMsg = "开始运行组件"
                map.data = ""
            } else {
                map.isSuccess = false
                map.errorCode = "2"
                map.errorMsg = "添加运行组件失败"
                map.data = ""
            }
        }
        render map as JSON
    }

    def listAt(Integer pageNum, Integer pageSize) {
        def map = [:]
        if (pageNum && pageSize) {
            def c = VideoInfo.createCriteria()
            def result = c.list(max: pageSize, offset: (pageNum - 1) * pageSize) {
                eq("infoKey", "at")
                order("id", "desc")
            }
            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = result
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = ""
        }
        render map as JSON
    }
}
