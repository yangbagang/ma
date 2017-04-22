package com.ybg.yxym.ma

import com.ybg.yxym.ma.jchat.JChatGroup
import grails.transaction.Transactional

@Transactional
class GroupMemberService {

    def addMembers(UserGroup userGroup, String userIds) {
        def userIdArray = userIds.split(",")
        def users = []
        userIdArray.each { userId ->
            def member = UserBase.get(Long.valueOf(userId))
            if (userGroup && member) {
                users.add("${member.ymCode}")
                def count = GroupMember.countByGroupAndMember(userGroup, member)
                if (count == 0) {
                    def groupMember = new GroupMember()
                    groupMember.group = userGroup
                    groupMember.member = member
                    groupMember.save flush: true
                }
            }
        }
        JChatGroup.addMembers(userGroup.groupId, users as String[])
    }

    def join(UserBase member, UserGroup userGroup) {
        def groupMember = new GroupMember()
        groupMember.group = userGroup
        groupMember.member = member
        groupMember.save flush: true

        def users = []
        users.add("${member.ymCode}")
        JChatGroup.addMembers(userGroup.groupId, users as String[])
    }

    def leave(UserBase member, UserGroup userGroup) {
        def groupMember = GroupMember.findByGroupAndMember(userGroup, member)
        if (groupMember) {
            groupMember.delete flush: true
            def users = []
            users.add("${member.ymCode}")
            JChatGroup.delMembers(userGroup.groupId, users as String[])
        }
    }

}
