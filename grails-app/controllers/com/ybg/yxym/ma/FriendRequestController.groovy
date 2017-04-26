package com.ybg.yxym.ma

import com.ybg.yxym.utils.UserUtil
import grails.converters.JSON

class FriendRequestController {

    /**
     * 获取好友添加请求
     * @param token
     * @return
     */
    def getRequestList(String token) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def requestList = FriendRequest.findAllByTargetUser(userBase)

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = requestList
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

    def acceptFriendRequest(String token, Long requestId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def friendRequest = FriendRequest.get(requestId)
            //TODO 发送一条通知消息
            if (userBase && friendRequest && friendRequest.targetUser == userBase) {
                friendRequest.status = 1 as Short
                friendRequest.save flush: true

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = ""
            } else {
                map.isSuccess = false
                map.message = "参数错误"
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

    def rejectFriendRequest(String token, Long requestId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def friendRequest = FriendRequest.get(requestId)
            //TODO 发送一条通知消息
            if (userBase && friendRequest && friendRequest.targetUser == userBase) {
                friendRequest.status = 2 as Short
                friendRequest.save flush: true

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = ""
            } else {
                map.isSuccess = false
                map.message = "参数错误"
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

    def createRequest(String token, Long userId, String reason) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def userBase2 = UserBase.get(userId)
            def friendRequest = FriendRequest.findByFromUserAndTargetUser(userBase, userBase2)
            if (userBase && userBase2) {
                if (friendRequest) {
                    map.isSuccess = false
                    map.message = "己经添加过了"
                    map.errorCode = "3"
                    map.data = "false"
                } else {
                    friendRequest = new FriendRequest()
                    friendRequest.fromUser = userBase
                    friendRequest.targetUser = userBase2
                    friendRequest.createTime = new Date()
                    friendRequest.reason = reason
                    friendRequest.save flush: true

                    //TODO 发送一条通知消息

                    map.isSuccess = true
                    map.message = ""
                    map.errorCode = "0"
                    map.data = ""
                }
            } else {
                map.isSuccess = false
                map.message = "参数错误"
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
