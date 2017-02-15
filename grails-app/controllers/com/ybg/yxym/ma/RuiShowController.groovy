package com.ybg.yxym.ma

import com.ybg.yxym.utils.QiNiuUtil
import com.ybg.yxym.utils.UserUtil
import grails.converters.JSON

class RuiShowController {

    def ruiShowService

    def showViewHistoryService

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
                if (type == 1 || type == 3) {
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
                def num = ruiShowService.ping(ruiShow, userBase, content)

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = "${num}"
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
                def zan = ShowZan.findByShowAndUserBase(ruiShow, userBase)
                if (zan) {
                    map.isSuccess = false
                    map.message = "己经赞过了，不能重复赞。"
                    map.errorCode = "3"
                    map.data = "false"
                } else {
                    def num = ruiShowService.zan(ruiShow, userBase)

                    map.isSuccess = true
                    map.message = ""
                    map.errorCode = "0"
                    map.data = "${num}"
                }
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
                def num = ruiShowService.share(ruiShow, userBase)

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = "${num}"
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
     * @param thumbnail 缩略图
     * @param title 美秀描述
     * @param type 类型//1图片2视频。直播请使用createLive
     * @return
     */
    def create(String token, String thumbnail, String title, Short type) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def show = ruiShowService.create(userBase, thumbnail, title, type)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = show
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 获取悦秀评论、点赞、分享数量，以及是否己经评论、点赞、是否己经分享、是否己经评论。
     * 以上数据保存在data中。pingNum评论数量，zanNum点赞数量，shareNum分享数量。
     * hasPing大于0表示己评论，等于0表示未评论；hasZan大于0表示己点赞，等于0表示未点赞；hasShare大于0表示己分享，0表示未分享。
     * @param token 用户token
     * @param showId 美秀ID
     */
    def getActionNum(String token, Long showId) {
        def map = [:]
        def show = RuiShow.get(showId)
        if (show && show.flag == 1 as Short) {
            show.viewNum = show.viewNum + 1
            show.save flush: true

            def num = ruiShowService.getActionNum(show, token)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = num
        } else {
            map.isSuccess = false
            map.message = "美秀不存在，请检查。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 创建直播
     * @param token
     * @param barId 美吧ID
     * @param thumbnail 封面
     * @param event 话题
     * @return
     */
    def createLive(String token, Long barId, String thumbnail, String event) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def ruiBar = RuiBar.get(barId)
            if (ruiBar) {
                def show = ruiShowService.createLive(userBase, ruiBar, thumbnail, event)
                showViewHistoryService.create(show, userBase)

                def stream = QiNiuUtil.createStream("live${show.id}")
                def url = QiNiuUtil.getRtmpPublishUrl("live${show.id}")
                def data = [:]
                data.show = show
                data.url = url
                println data.url

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = data
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

    /**
     * 观看直播
     * @param showId
     * @param token
     * @return
     */
    def showLive(Long showId, String token) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def show = RuiShow.get(showId)
            if (show && show.flag == 1 as Short) {
                show.viewNum = show.viewNum + 1
                show.save flush: true

                def userList = showViewHistoryService.listUser(show)
                showViewHistoryService.create(show, userBase)

                def url = QiNiuUtil.getRtmpLiveUrl("live${show.id}")
                def data = [:]
                data.show = show
                data.url = url
                data.userList = userList

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = data
            } else {
                map.isSuccess = false
                map.message = "直播己经结束。"
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
     * 停止直播
     * @param showId
     * @param token
     * @return
     */
    def closeLive(Long showId, String token) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def show = RuiShow.get(showId)
            if (show && show.flag == 1 as Short && show.userBase == userBase) {
                show.flag = Short.valueOf("0")
                show.updateTime = new Date()
                show.save flush: true

                def num = showViewHistoryService.getViewNum(show)

                def key = "live${show.id}"
                def stream = QiNiuUtil.getStream(key)
                QiNiuUtil.disableStream(stream, key)

                def data = [:]
                data.show = show
                data.num = num

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = data
            } else {
                map.isSuccess = false
                map.message = "直播己经结束。"
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
     * 离开直播
     * @param showId
     * @param token
     * @return
     */
    def leaveLive(Long showId, String token) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def show = RuiShow.get(showId)
            if (show) {
                showViewHistoryService.drop(show, userBase)

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = ""
            } else {
                map.isSuccess = false
                map.message = "直播不存在。"
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
     * 直播中发送消息
     * @param token 发送者token
     * @param showId 直播秀id
     * @param flag 1发送给主播，0发送给所有参考直播用户。
     * @param content 消息内容
     */
    def sendLiveMsg(String token, Long showId, Integer flag, String content) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def show = RuiShow.get(showId)
                if (show) {
                    ruiShowService.sendLiveMsg(show, userBase, flag, content)
                    map.isSuccess = true
                    map.message = ""
                    map.errorCode = "0"
                    map.data = "true"
                } else {
                    map.isSuccess = false
                    map.message = "直播不存在"
                    map.errorCode = "3"
                    map.data = "false"
                }
            } else {
                map.isSuccess = false
                map.message = "用户不存在"
                map.errorCode = "2"
                map.data = "false"
            }
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录"
            map.errorCode = "1"
            map.data = "false"
        }

        render map as JSON
    }
}
