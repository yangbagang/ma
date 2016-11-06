package com.ybg.yxym.ma

import com.ybg.yxym.utils.UserUtil
import grails.converters.JSON

class RuiShowController {

    def ruiShowService

    def list(Integer pageNum, Integer pageSize, Integer type) {
        def map = [:]
        if (pageNum && pageSize && type) {
            def c = RuiShow.createCriteria()
            def result = c.list(max: pageSize, offset: (pageNum - 1) * pageSize) {
                eq("flag", 1 as Short)
                if (type == 1) {
                    order("createTime", "desc")
                } else (type == 2) {
                    order("viewNum", "desc")
                }
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

    /**
     * 评价秀
     * @param token
     * @param showId
     * @param content
     * @return
     */
    def ping(String token, Long showId, String content) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def ruiShow = RuiShow.get(showId)
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (ruiShow && ruiShow.flag == 1 as Short) {
                def showPing = ruiShowService.ping(ruiShow, userBase, content)

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = showPing
            } else {
                map.isSuccess = false
                map.message = "美秀不存在，请检查。"
                map.errorCode = "2"
                map.data = false
            }
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录。"
            map.errorCode = "1"
            map.data = false
        }
        render map as JSON
    }

    /**
     * 赞秀
     * @param token
     * @param showId
     * @return
     */
    def zan(String token, Long showId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def ruiShow = RuiShow.get(showId)
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (ruiShow && ruiShow.flag == 1 as Short) {
                def showZan = ruiShowService.zan(ruiShow, userBase)

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = showZan
            } else {
                map.isSuccess = false
                map.message = "美秀不存在，请检查。"
                map.errorCode = "2"
                map.data = false
            }
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录。"
            map.errorCode = "1"
            map.data = false
        }
        render map as JSON
    }

    /**
     * 分享秀
     * @param token
     * @param showId
     * @return
     */
    def share(String token, Long showId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def ruiShow = RuiShow.get(showId)
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (ruiShow && ruiShow.flag == 1 as Short) {
                def showShare = ruiShowService.share(ruiShow, userBase)

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = showShare
            } else {
                map.isSuccess = false
                map.message = "美秀不存在，请检查。"
                map.errorCode = "2"
                map.data = false
            }
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录。"
            map.errorCode = "1"
            map.data = false
        }
        render map as JSON
    }

    /**
     * 显示详情
     * @param showId
     * @return
     */
    def detail(Long showId) {
        def map = [:]
        def show = RuiShow.get(showId)
        if (show && show.flag == 1 as Short) {
            def files = ShowFile.findAllByShowAndFlag(show, 1 as Short)
            show.files = files

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = show
        } else {
            map.isSuccess = false
            map.message = "美秀不存在，请检查。"
            map.errorCode = "1"
            map.data = false
        }
        render map as JSON
    }
}
