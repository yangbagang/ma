package com.ybg.yxym.ma

import com.ybg.yxym.utils.UserUtil
import grails.converters.JSON

class GroupMemberController {

    GroupMemberService groupMemberService

    /**
     * 列出群成员
     * @param groupId
     * @return
     */
    def list(Long groupId) {
        def map = [:]
        def userGroup = UserGroup.get(groupId)
        if (userGroup) {
            def data = GroupMember.findAllByGroup(userGroup)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = data
        } else {
            map.isSuccess = false
            map.message = "参数错误"
            map.errorCode = "1"
            map.data = "false"
        }

        render map as JSON
    }

    /**
     * 添加成员
     * @param token
     * @param groupId
     * @param userId
     * @return
     */
    def add(String token, Long groupId, String userIds) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def group = UserGroup.get(groupId)
            if (userBase && group && userIds) {
                if (userBase == group.userBase) {
                    groupMemberService.addMembers(group, userIds)

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
     * 加入群
     * @param token
     * @param groupId
     * @return
     */
    def join(String token, Long groupId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def group = UserGroup.get(groupId)
            if (userBase && group) {
                def count = GroupMember.countByGroupAndMember(group, userBase)
                if (count == 0) {
                    groupMemberService.join(userBase, group)

                    map.isSuccess = true
                    map.message = ""
                    map.errorCode = "0"
                    map.data = ""
                } else {
                    map.isSuccess = false
                    map.message = "你己经是该群成员"
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
     * 设置在群中的昵称
     * @param token
     * @param groupId
     * @param nickName
     * @return
     */
    def setNickName(String token, Long groupId, String nickName) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def group = UserGroup.get(groupId)
            if (userBase && group) {
                def member = GroupMember.findByGroupAndMember(group, userBase)
                if (member) {
                    member.nickName = nickName
                    member.save flush: true

                    map.isSuccess = true
                    map.message = ""
                    map.errorCode = "0"
                    map.data = ""
                } else {
                    map.isSuccess = false
                    map.message = "你还不是该群成员"
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
     * 退出群
     * @param token
     * @param groupId
     * @return
     */
    def leave(String token, Long groupId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def group = UserGroup.get(groupId)
            if (userBase && group) {
                if (userBase != group.userBase) {
                    groupMemberService.leave(userBase, group)

                    map.isSuccess = true
                    map.message = ""
                    map.errorCode = "0"
                    map.data = ""
                } else {
                    map.isSuccess = false
                    map.message = "你是该群管理员，不能退出。"
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
