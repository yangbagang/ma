package com.ybg.yxym.ma

import com.ybg.yxym.utils.UserUtil
import grails.converters.JSON

class UserGiftController {

    UserGiftService userGiftService

    /**
     * 列出收到的礼物。带分页。
     * @param token
     * @param pageNum 显示第几页
     * @param pageSize 每页显示多少条
     * @return
     */
    def listReceiver(String token, Integer pageNum, Integer pageSize) {
        def map = [:]
        if (pageNum && pageSize && token) {
            if (UserUtil.checkToken(token)) {
                def userBase = UserBase.get(UserUtil.getUserId(token))
                if (userBase) {
                    def c = UserGift.createCriteria()
                    def result = c.list(max: pageSize, offset: (pageNum - 1) * pageSize) {
                        eq("targetUser", userBase)
                        order("createTime", "desc")
                    }
                    map.isSuccess = true
                    map.message = ""
                    map.errorCode = "0"
                    map.data = result
                } else {
                    map.isSuccess = false
                    map.message = "用户不存在"
                    map.errorCode = "3"
                    map.data = "false"
                }
            } else {
                map.isSuccess = false
                map.message = "登录凭证失效，请重新登录"
                map.errorCode = "2"
                map.data = "false"
            }
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = ""
        }
        render map as JSON
    }

    /**
     * 列出送出的礼物。带分页。
     * @param token
     * @param pageNum 显示第几页
     * @param pageSize 每页显示多少条
     * @return
     */
    def listSend(String token, Integer pageNum, Integer pageSize) {
        def map = [:]
        if (pageNum && pageSize && token) {
            if (UserUtil.checkToken(token)) {
                def userBase = UserBase.get(UserUtil.getUserId(token))
                if (userBase) {
                    def c = UserGift.createCriteria()
                    def result = c.list(max: pageSize, offset: (pageNum - 1) * pageSize) {
                        eq("fromUser", userBase)
                        order("createTime", "desc")
                    }
                    map.isSuccess = true
                    map.message = ""
                    map.errorCode = "0"
                    map.data = result
                } else {
                    map.isSuccess = false
                    map.message = "用户不存在"
                    map.errorCode = "3"
                    map.data = "false"
                }
            } else {
                map.isSuccess = false
                map.message = "登录凭证失效，请重新登录"
                map.errorCode = "2"
                map.data = "false"
            }
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = ""
        }
        render map as JSON
    }

    /**
     * 送礼。错误码为4表示余额不足，需要引导到充值界面。
     * @param token 发送者token
     * @param userId 接收者id
     * @param giftId 礼物id
     * @return
     */
    def sendGift(String token, Long userId, Long giftId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def receiver = UserBase.get(userId)
                def gift = RuiGift.get(giftId)
                if (receiver && gift) {
                    def userFortune = UserFortune.findByUserBase(userBase)
                    if (userFortune && userFortune.meiPiao >= gift.realPrice) {
                        userGiftService.sendGift(userBase, receiver, gift)

                        map.isSuccess = true
                        map.message = ""
                        map.errorCode = "0"
                        map.data = "true"
                    } else {
                        map.isSuccess = false
                        map.message = "余额不足"
                        map.errorCode = "4"
                        map.data = "false"
                    }
                } else {
                    map.isSuccess = false
                    map.message = "参数错误"
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
