package com.ybg.yxym.ma

import grails.transaction.Transactional

@Transactional
class UserGroupService {

    def list(UserBase userBase) {
        GroupMember.findAllByMember(userBase)*.group
    }

    def create(UserBase userBase, String name, Long groupId) {
        def group = new UserGroup()
        group.userBase = userBase
        group.name = name
        group.groupId = groupId
        group.save flush: true

        def member = new GroupMember()
        member.role = 3
        member.member = userBase
        member.group = group
        member.save flush: true
    }

    def dissolve(UserGroup userGroup) {
        def members = GroupMember.findAllByGroup(userGroup)
        members.each { member ->
            member.delete flush: true
        }
        userGroup.delete flush: true
    }

    def transfer(UserBase oldAdmin, UserBase newAdmin, UserGroup userGroup) {
        if (userGroup) {
            userGroup.userBase = newAdmin
            userGroup.save flush: true
        }

        def member1 = GroupMember.findByGroupAndMember(userGroup, oldAdmin)
        if (member1) {
            member1.role = 0
            member1.save flush: true
        }

        def member2 = GroupMember.findByGroupAndMember(userGroup, newAdmin)
        if (member2) {
            member2.role = 3
            member2.save flush: true
        }
    }

}
