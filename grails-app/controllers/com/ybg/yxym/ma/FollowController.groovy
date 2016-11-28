package com.ybg.yxym.ma

import com.ybg.yxym.utils.UserUtil
import grails.converters.JSON

class FollowController {

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
                    def instance = new Follow()
                    instance.userBase = user
                    instance.follow = follow
                    instance.createTime = new Date()
                    instance.save flush: true

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
}
