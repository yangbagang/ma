package com.ybg.yxym.ma

import com.ybg.yxym.utils.UserUtil
import grails.transaction.Transactional

import java.text.SimpleDateFormat

@Transactional
class UserBaseService {

    def getUMToken(String openid, String platform) {
        def userBase = UserBase.findByPlatformIdAndPlatform(openid, platform)
        if (!userBase) {
            userBase = new UserBase()
            userBase.platform = platform
            userBase.platformId = openid
            userBase.save flush: true
        }
        UserUtil.createToken(userBase.id)
    }

    def completeUserBase(String token, String birthday, String nickName, Integer sex, String avatar) {
        def userId = UserUtil.getUserId(token)
        def userBase = UserBase.get(userId)
        def userInfo = UserInfo.findByUserBase(userBase)
        def userBaseChanged = false

        if (avatar && avatar.length() > 5) {
            userBase.avatar = avatar
            userBaseChanged = true
        }
        if (nickName && nickName.length() > 0) {
            userBase.nickName = nickName
            userBaseChanged = true
        }
        if (userBaseChanged) {
            userBase.save flush: true
        }

        def userInfoChanged = false
        try {
            def sdf = new SimpleDateFormat("yyyy-MM-dd")
            if (birthday && birthday.length() == 10) {
                userInfo.birthday = sdf.parse(birthday)
                userInfoChanged = true
            }
        } catch (Exception e) {
            //
        }
        if (sex == 1 || sex == 0) {
            userInfo.sex = sex
            userInfoChanged = true
        }
        if (userInfoChanged) {
            userInfo.save flush: true
        }
    }

    def loadAuthorInfo(RuiShow show, String token) {
        def userBase = [:]
        if (show.userBase) {
            userBase.id = show.userBase.id
            userBase.nickName = show.userBase.nickName
            userBase.ymCode = show.userBase.ymCode
            userBase.avatar = show.userBase.avatar
            if (token == null || token == "") {
                userBase.flag = 0
            } else {
                def user = UserBase.get(UserUtil.getUserId(token))
                if (user) {
                    def count = Follow.countByUserBaseAndFollow(show.userBase, user)
                    userBase.flag = count
                } else {
                    userBase.flag = 0
                }
            }
            //计算美力值
            def sum = 0
            MeiLiHistory.findAllByUserBaseAndShowId(show.userBase, show.id).each { history ->
                sum += history.score
            }
            userBase.ml = sum
            def meiLi = UserMeiLi.findByUserBase(show.userBase)
            if (meiLi) {
                userBase.score = meiLi.meiLi
            } else {
                userBase.score = 0
            }
        }
        userBase
    }

}
