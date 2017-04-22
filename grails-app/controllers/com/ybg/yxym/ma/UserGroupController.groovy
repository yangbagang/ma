package com.ybg.yxym.ma

import com.ybg.yxym.ma.jchat.JChatGroup
import com.ybg.yxym.utils.UserUtil
import grails.converters.JSON

class UserGroupController {

    UserGroupService userGroupService

    /**
     * 列出用户添加的群
     * @param token
     * @return
     */
    def list(String token) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def data = userGroupService.list(userBase)

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
     * 创建群
     * @param token
     * @param name
     * @return
     */
    def create(String token, String name) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                //先创外部群
                def groupId = JChatGroup.create("${userBase.ymCode}", name)
                userGroupService.create(userBase, name, groupId)

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = ""
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
     * 解散群
     * @param token
     * @param groupId
     * @return
     */
    def dissolve(String token, Long groupId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def group = UserGroup.get(groupId)
            if (userBase && group) {
                if (userBase == group.userBase) {
                    JChatGroup.remove(group.groupId)
                    userGroupService.dissolve(group)

                    map.isSuccess = true
                    map.message = ""
                    map.errorCode = "0"
                    map.data = ""
                } else {
                    map.isSuccess = false
                    map.message = "你不是该群管理员"
                    map.errorCode = "3"
                    map.data = "false"
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

    /**
     * 转让群
     * @param token
     * @param userId
     * @param groupId
     * @return
     */
    def transfer(String token, Long userId, Long groupId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def group = UserGroup.get(groupId)
            def user = UserBase.get(userId)
            if (userBase && group && user) {
                if (userBase == group.userBase) {
                    userGroupService.transfer(userBase, user, group)

                    map.isSuccess = true
                    map.message = ""
                    map.errorCode = "0"
                    map.data = ""
                } else {
                    map.isSuccess = false
                    map.message = "你不是该群管理员"
                    map.errorCode = "3"
                    map.data = "false"
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
