package com.ybg.yxym.ma.jchat

import cn.jiguang.common.resp.APIConnectionException
import cn.jiguang.common.resp.APIRequestException
import cn.jmessage.api.common.model.RegisterInfo
import cn.jmessage.api.common.model.UserPayload
import org.slf4j.LoggerFactory

/**
 * Created by yangbagang on 2017/4/20.
 */
class JChatUser extends JChatBase {

    private static LOG = LoggerFactory.getLogger(JChatUser.class)

    static register(String ymCode, String token) {
        try {
            def users = []
            def user = RegisterInfo.newBuilder()
                    .setUsername(ymCode)
                    .setPassword(token)
                    .build()
            users.add(user)

            String res = client.registerUsers(users as RegisterInfo[])
            LOG.info(res)
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e)
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e)
            LOG.info("HTTP Status: " + e.getStatus())
            LOG.info("Error Message: " + e.getMessage())
        }
    }

    static getInfo(String ymCode) {
        def info = null
        try {
            info = client.getUserInfo(ymCode)
            LOG.info(info.getUsername())
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e)
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e)
            LOG.info("HTTP Status: " + e.getStatus())
            LOG.info("Error Message: " + e.getMessage())
        }
        info
    }

    static getState(String ymCode) {
        def state = null
        try {
            state = client.getUserState(ymCode)
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e)
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e)
            LOG.info("HTTP Status: " + e.getStatus())
            LOG.info("Error Message: " + e.getMessage())
        }
        state
    }

    static updatePassword(String ymCode, String token) {
        try {
            client.updateUserPassword(ymCode, token)
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e)
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e)
            LOG.info("HTTP Status: " + e.getStatus())
            LOG.info("Error Message: " + e.getMessage())
        }
    }

    static updateInfo(String ymCode, String nickName, String avatar) {
        def userPayload = UserPayload.newBuilder()
        def isChanged = false
        if (nickName != null && nickName != "" && nickName != "null") {
            userPayload.nickname = nickName
            isChanged = true
        }
        if (avatar != null && avatar != "" && avatar != "null") {
            userPayload.signature = avatar
            isChanged = true
        }
        if (isChanged) {
            client.updateUserInfo(ymCode, userPayload.build())
        }
    }

}
