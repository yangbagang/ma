package com.ybg.yxym.ma

import com.ybg.yxym.utils.UserUtil
import grails.converters.JSON

class FriendController {

    /**
     * 获取用户好友列表
     * @param token
     * @return
     */
    def getFriendList(String token) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def userList = Friend.findAllByUserBase(userBase)*.friend

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = userList
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
     * 查询是否是好友
     * @param token
     * @param ymCode
     * @return
     */
    def check(String token, Long ymCode) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase1 = UserBase.get(UserUtil.getUserId(token))
            def userBase2 = UserBase.findByYmCode(ymCode)
            if (userBase1 && userBase2) {
                def count = Friend.countByUserBaseAndFriend(userBase1, userBase2)
                def result = count > 0

                map.isSuccess = result
                map.message = ""
                map.errorCode = "0"
                map.data = "${result}"
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
     * 获取好友信息
     * @param token
     * @param ymCode
     * @return
     */
    def getFriend(String token, Long ymCode) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase1 = UserBase.get(UserUtil.getUserId(token))
            def userBase2 = UserBase.findByYmCode(ymCode)
            if (userBase1 && userBase2) {
                def friend = Friend.findByUserBaseAndFriend(userBase1, userBase2)
                def result = [:]
                result.userBase = userBase2
                result.friend = friend
                result.userInfo = UserInfo.findByUserBase(userBase2)

                map.isSuccess = result
                map.message = ""
                map.errorCode = "0"
                map.data = result
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
     * 改变提醒状态
     * @param token
     * @param friendId
     * @param disturbing 1，提醒，0不提醒
     * @return
     */
    def changeDisturbState(String token, Long friendId, Short disturbing) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def friend = Friend.get(friendId)
            if (userBase && friend && userBase == friend.userBase) {
                friend.disturbing = disturbing
                friend.save flush: true

                //TODO 同步IM

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = "true"
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

    /**
     * 改变黑名单状态
     * @param token
     * @param friendId
     * @param inBlacklist 1黑名单0非黑名单
     * @return
     */
    def changeBlackListState(String token, Long friendId, Short inBlacklist) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def friend = Friend.get(friendId)
            if (userBase && friend && userBase == friend.userBase) {
                friend.inBlacklist = inBlacklist
                friend.save flush: true

                //TODO 同步IM

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = "true"
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

    /**
     * 删除好友
     * @param token
     * @param friendId
     * @return
     */
    def delete(String token, Long friendId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def friend = Friend.get(friendId)
            if (userBase && friend && userBase == friend.userBase) {
                def friend2 = Friend.findByUserBaseAndFriend(friend.friend, userBase)
                if (friend2) {
                    friend2.delete flush: true
                }
                friend.delete flush: true

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = "true"
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
