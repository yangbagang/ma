package com.ybg.yxym.ma

import com.ybg.yxym.utils.UserUtil
import grails.converters.JSON

class UserDateController {

    /**
     * 查看约会详情
     * @param token
     * @param dateId
     * @return
     */
    def viewDetail(String token, Long dateId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def date = UserDate.get(dateId)
                if (date) {
                    map.isSuccess = true
                    map.message = ""
                    map.errorCode = "0"
                    map.data = date
                } else {
                    map.isSuccess = false
                    map.message = "约会不存在"
                    map.errorCode = "3"
                    map.data = ""
                }
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
     * 接受约会
     * @param token
     * @param dateId
     * @return
     */
    def accept(String token, Long dateId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def date = UserDate.get(dateId)
                if (date) {
                    date.flag = 1
                    date.save flush: true

                    map.isSuccess = true
                    map.message = ""
                    map.errorCode = "0"
                    map.data = date
                } else {
                    map.isSuccess = false
                    map.message = "约会不存在"
                    map.errorCode = "3"
                    map.data = ""
                }
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
     * 拒绝约会
     * @param token
     * @param dateId
     * @return
     */
    def reject(String token, Long dateId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def date = UserDate.get(dateId)
                if (date) {
                    date.flag = 2
                    date.save flush: true

                    map.isSuccess = true
                    map.message = ""
                    map.errorCode = "0"
                    map.data = date
                } else {
                    map.isSuccess = false
                    map.message = "约会不存在"
                    map.errorCode = "3"
                    map.data = ""
                }
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
     * 忽略约会请求
     * @param token
     * @param dateId
     * @return
     */
    def ignore(String token, Long dateId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def date = UserDate.get(dateId)
                if (date) {
                    date.flag = 3
                    date.save flush: true

                    map.isSuccess = true
                    map.message = ""
                    map.errorCode = "0"
                    map.data = date
                } else {
                    map.isSuccess = false
                    map.message = "约会不存在"
                    map.errorCode = "3"
                    map.data = ""
                }
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
