package com.ybg.yxym.ma

import grails.transaction.Transactional

@Transactional
class FollowService {

    def follow(UserBase userBase1, UserBase userBase2) {
        def f = Follow.findByUserBaseAndFollow(userBase1, userBase2)
        if (f == null) {
            def instance = new Follow()
            instance.userBase = userBase1
            instance.follow = userBase2
            instance.createTime = new Date()
            instance.save flush: true
        }
        def f2 = Follow.findByUserBaseAndFollow(userBase2, userBase1)
        if (f2) {
            def friend1 = new Friend()
            friend1.userBase = userBase2
            friend1.friend = userBase1
            friend1.save flush: true

            def friend2 = new Friend()
            friend2.userBase = userBase1
            friend2.friend = userBase2
            friend2.save flush: true
        }
    }

}
