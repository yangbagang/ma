package com.ybg.yxym.ma

import com.ybg.yxym.utils.UserUtil
import grails.converters.JSON
import io.rong.RongCloud
import io.rong.messages.TxtMessage
import io.rong.messages.VoiceMessage

class IMController {

    private rongCloud = RongCloud.instance

    private picServer = "https://139.224.186.241:8443/fileserver/file/preview/"

    /**
     * 获得IM token
     * @param token
     * @return
     */
    def getIMToken(String token) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def avatar = ""
                if (userBase.avatar && userBase.avatar != "") {
                    avatar = picServer + userBase.avatar
                }
                // 获取 Token 方法
                def userGetTokenResult = rongCloud.user.getToken("${userBase.id}", userBase.nickName, avatar)

                map.isSuccess = userGetTokenResult.code == 200
                map.message = userGetTokenResult.errorMessage
                map.errorCode = userGetTokenResult.code
                map.data = userGetTokenResult.token
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

    /**
     * 获得IM token
     * @param token
     * @return
     */
    def userRefresh(String token) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def avatar = ""
                if (userBase.avatar && userBase.avatar != "") {
                    avatar = picServer + userBase.avatar
                }
                // 刷新用户信息方法
                def userRefreshResult = rongCloud.user.refresh("${userBase.id}", userBase.nickName, avatar)

                map.isSuccess = userRefreshResult.code == 200
                map.message = userRefreshResult.errorMessage
                map.errorCode = userRefreshResult.code
                map.data = "${userRefreshResult.code == 200}"
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

    /**
     * 发送语音消息
     * @param token
     * @param userIds 接收者id，逗号分开。
     * @return
     */
    def sendVoiceMsg(String token, String userIds, String content, Long duration) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def messagePublishPrivateVoiceMessage = new VoiceMessage(content, "", duration)
                // 发送单聊消息方法
                def messagePublishPrivateResult = rongCloud.message.publishPrivate("${userBase.id}", userIds.split(","),
                    messagePublishPrivateVoiceMessage, "消息通知", "{\"pushData\":\"消息\"}", "1", 0, 0, 0, 0)

                map.isSuccess = messagePublishPrivateResult.code == 200
                map.message = messagePublishPrivateResult.errorMessage
                map.errorCode = messagePublishPrivateResult.code
                map.data = "${messagePublishPrivateResult.code == 200}"
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

    /**
     * 发送群组消息
     * @param token
     * @param userIds 接收者id，逗号分开。
     * @return
     */
    def sendGroupTxtMsg(String token, String groupIds, String content, Long duration) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def messagePublishGroupTxtMessage = new TxtMessage(content, "")
                // 发送单聊消息方法
                def messagePublishGroupResult = rongCloud.message.publishGroup("${userBase.id}", groupIds.split(","),
                        messagePublishGroupTxtMessage, "消息通知", "{\"pushData\":\"消息\"}", 1, 0, 0)

                map.isSuccess = messagePublishGroupResult.code == 200
                map.message = messagePublishGroupResult.errorMessage
                map.errorCode = messagePublishGroupResult.code
                map.data = "${messagePublishGroupResult.code == 200}"
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
