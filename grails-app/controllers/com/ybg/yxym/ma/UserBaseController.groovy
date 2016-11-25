package com.ybg.yxym.ma

import com.ybg.yxym.utils.UserUtil
import grails.converters.JSON
import org.apache.commons.codec.digest.DigestUtils

class UserBaseController {

    def userBaseService
    def yueMeiCodeService

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
                map.data = "false"
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
                    map.data = "false"
                }
            }
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = "false"
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
                user.ymCode = Long.valueOf(yueMeiCodeService.getYMCode())
                user.nickName = "悦美" + user.ymCode
                user.ymUser = mobile
                user.mobile = mobile
                user.password = DigestUtils.sha256Hex(password)
                user.flag = 1 as Short
                user.save flush: true
                //生成扩展实例
                def userInfo = new UserInfo()
                userInfo.userBase = user
                userInfo.save flush: true

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
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 获得用户基本信息
     * @param token 用户token
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
            map.data = "false"
        }

        render map as JSON
    }

    /**
     * 补充资料
     * @param token 用户token
     * @param birthday 生日
     * @param nickName 呢称
     * @param sex 性别。1为男0为女
     * @param avatar 用户头像字符串（上传到文件服务器的返回字符串）
     * @return
     */
    def completeData(String token, String birthday, String nickName, Integer sex, String avatar) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            userBaseService.completeUserBase(token, birthday, nickName, sex, avatar)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = "true"
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录"
            map.errorCode = "1"
            map.data = "false"
        }

        render map as JSON
    }

    /**
     * 第三方登录
     * @param openid 第三方唯一字符识别码
     * @param platform 第三方平台名称
     * @param nickName 呢称
     * @param userImage 头像
     * @param sex 性别，1为男0为女
     */
    def umLogin(String openid, String platform, String nickName, String userImage, String sex) {
        def map = [:]
        if (openid && platform) {
            def token = userBaseService.getUMToken(openid, platform)
            userBaseService.completeUserBase(token, null, nickName, Integer.valueOf(sex), userImage)
            map.isSuccess = true
            map.message = "登录完成"
            map.errorCode = "0"
            map.data = token
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }
}
