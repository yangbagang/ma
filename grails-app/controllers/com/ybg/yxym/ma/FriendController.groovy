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
}
