package com.ybg.yxym.ma

import com.ybg.yxym.utils.UserUtil
import grails.converters.JSON

class RuiShowController {

    def ruiShowService

    /**
     * 列出美秀。带分页。
     * @param pageNum 显示第几页
     * @param pageSize 每页显示多少条
     * @param type 按哪种排序。1为最新，2为最热。
     * @return
     */
    def list(Integer pageNum, Integer pageSize, Integer type) {
        def map = [:]
        if (pageNum && pageSize && type) {
            def c = RuiShow.createCriteria()
            def result = c.list(max: pageSize, offset: (pageNum - 1) * pageSize) {
                eq("flag", 1 as Short)
                if (type == 1) {
                    order("createTime", "desc")
                } else if (type == 2) {
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
     * @param token 用户token
     * @param showId 美秀ID
     * @param content 评价内容
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
                map.data = "false"
            }
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 赞秀
     * @param token 用户token
     * @param showId 美秀ID
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
                map.data = "false"
            }
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 分享秀
     * @param token 用户token
     * @param showId 美秀ID
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
                map.data = "false"
            }
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 显示美秀详细资料，包含相关附件，如附加的图片等。但不包括用户信息。
     * @param showId 美秀ID
     * @return
     */
    def detail(Long showId) {
        def map = [:]
        def show = RuiShow.get(showId)
        if (show && show.flag == 1 as Short) {
            show.viewNum = show.viewNum + 1
            show.save flush: true

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
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 发布一条美秀
     * @param token 用户token
     * @param barId 美秀版块ID
     * @param thumbnail 缩略图
     * @param title 美秀描述
     * @return
     */
    def create(String token, Long barId, String thumbnail, String title, Short type) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def ruiBar = RuiBar.get(barId)
            if (ruiBar) {
                def show = ruiShowService.create(userBase, ruiBar, thumbnail, title, type)

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = show
            } else {
                map.isSuccess = false
                map.message = "美吧不存在，请检查。"
                map.errorCode = "2"
                map.data = "false"
            }
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }
}
