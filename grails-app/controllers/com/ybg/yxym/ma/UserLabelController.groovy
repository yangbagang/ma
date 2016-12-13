package com.ybg.yxym.ma

import com.ybg.yxym.utils.UserUtil
import grails.converters.JSON

class UserLabelController {

    def userLabelService

    /**
     * 获得用户标签
     * @param token 用户token
     * @return 返回值中label表示名称，catalog表示分类，暂时不处理，flag=1表示己经勾选。
     */
    def list(String token) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def data = userLabelService.list(userBase)

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
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

    /**
     * 更新用户标签
     * 只上传己选定标签，上传参数名为labels，参数类型为字符串数组
     * @param token
     * @return
     */
    def update(String token) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def names = request.getParameterValues("labels")
                userLabelService.update(userBase, names)

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = "true"
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
