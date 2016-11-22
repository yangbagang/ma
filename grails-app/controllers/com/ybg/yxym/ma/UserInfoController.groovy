package com.ybg.yxym.ma

import com.ybg.yxym.utils.UserUtil
import grails.converters.JSON

class UserInfoController {

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    def getUserInfo(String token) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userId = UserUtil.getUserId(token)
            def userBase = UserBase.get(userId)
            def userInfo = UserInfo.findByUserBase(userBase)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = userInfo
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录"
            map.errorCode = "1"
            map.data = "false"
        }

        render map as JSON
    }
}
