package com.ybg.yxym.ma

import com.ybg.yxym.utils.UserUtil
import grails.converters.JSON

class FollowController {

    def followService

    /**
     * 关注
     * @param token 用户token
     * @param userId 被关注userId
     */
    def follow(String token, Long userId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def user = UserBase.get(userId)
            if (user && user.flag == 1 as Short) {
                def follow = UserBase.get(UserUtil.getUserId(token))
                if (user == follow) {
                    map.isSuccess = false
                    map.message = "你不能关注自己"
                    map.errorCode = "3"
                    map.data = "false"
                } else {
                    followService.follow(user, follow)

                    map.isSuccess = true
                    map.message = ""
                    map.errorCode = "0"
                    map.data = "true"
                }
            } else {
                map.isSuccess = false
                map.message = "被关注用户不存在"
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
     * 获取指定用户的关注数量。即这个用户关注了多少人。
     * @param userId
     * @return
     */
    def getFollowNum(Long userId) {
        def map = [:]
        def userBase = UserBase.get(userId)
        if (userBase) {
            def num = Follow.countByFollow(userBase)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = "${num}"
        } else {
            map.isSuccess = false
            map.message = "指定用户不存在"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 获取指定用户的粉丝数量，即有多少人关注了该用户。
     * @param userId
     * @return
     */
    def getFansNum(Long userId) {
        def map = [:]
        def userBase = UserBase.get(userId)
        if (userBase) {
            def num = Follow.countByUserBase(userBase)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = "${num}"
        } else {
            map.isSuccess = false
            map.message = "指定用户不存在"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    def checkFollowStatus(String token, Long userId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def user = UserBase.get(userId)
            if (user && user.flag == 1 as Short) {
                def data = [:]
                def myself = UserBase.get(UserUtil.getUserId(token))
                def follow = Follow.findByUserBaseAndFollow(user, myself)
                if (follow) {
                    data.isFollowed = 1
                } else {
                    data.isFollowed = 0
                }
                def friend = Friend.findByUserBaseAndFriend(user, myself)
                if (friend) {
                    data.isFriend = 1
                } else {
                    data.isFriend = 0
                }

                data.isFollow =
                map.isSuccess = true
                map.message = ""
                map.errorCode = ""
                map.data = data
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
