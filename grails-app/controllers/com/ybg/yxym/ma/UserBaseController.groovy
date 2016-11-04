package com.ybg.yxym.ma

import com.ybg.yxym.utils.UserUtil
import grails.converters.JSON
import org.apache.commons.codec.digest.DigestUtils

class UserBaseController {

    /**
     * 用户登录
     * @param mobile 手机号
     * @param password 密码
     */
    def login(String mobile, String password) {
        def map = [:]
        if (mobile && password) {
            def user = UserBase.findByMobile(mobile)
            if (!user) {
                map.isSuccess = false
                map.message = "用户名或密码错误"
                map.errorCode = "2"
                map.data = ""
            } else {
                //生成实例
                def pwd = DigestUtils.sha256Hex(password)
                if (pwd == user.password) {
                    //构造返回数据
                    def token = UserUtil.createToken(user.id)
                    map.isSuccess = true
                    map.message = "登录完成"
                    map.errorCode = "0"
                    map.data = token
                } else {
                    map.isSuccess = false
                    map.message = "用户名或密码错误"
                    map.errorCode = "2"
                    map.data = ""
                }
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
     * 用户注册
     * @param mobile 手机号
     * @param password 密码
     */
    def register(String mobile, String password) {
        def map = [:]
        if (mobile && password) {
            def user = UserBase.findByMobile(mobile)
            if (user) {
                map.isSuccess = false
                map.message = "该手机己经注册"
                map.errorCode = "2"
                map.data = ""
            } else {
                //生成实例
                user = new UserBase()
                user.ymCode = UserUtil.createYmCode()
                user.ymUser = mobile
                user.mobile = mobile
                user.password = DigestUtils.sha256Hex(password)
                user.flag = 1 as Short
                user.save flush: true

                //构造返回数据
                def token = UserUtil.createToken(user.id)
                map.isSuccess = true
                map.message = "注册完成"
                map.errorCode = "0"
                map.data = token
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
     * 获得用户基本信息
     * @param token
     */
    def getUserBase(String token) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userId = UserUtil.getUserId(token)
            def userBase = UserBase.get(userId)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = userBase
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录"
            map.errorCode = "1"
            map.data = ""
        }

        render map as JSON
    }
}
