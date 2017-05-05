package com.ybg.yxym.ma

import com.ybg.yxym.utils.MeiliConstant
import grails.transaction.Transactional

@Transactional
class UserGiftService {

    def sendGift(UserBase sender, UserBase receiver, RuiGift gift) {
        //减美票
        def senderFortune = UserFortune.findByUserBase(sender)
        senderFortune.meiPiao -= gift.realPrice
        senderFortune.save flush: true
        //加美豆
        def receiverFortune = UserFortune.findByUserBase(receiver)
        receiverFortune.meiDou += gift.realPrice
        receiverFortune.save flush: true
        //加历史
        def userGift = new UserGift()
        userGift.gift = gift
        userGift.fromUser = sender
        userGift.targetUser = receiver
        userGift.createTime = new Date()
        userGift.save flush: true

        def userFortuneHistory = new UserFortuneHistory()
        userFortuneHistory.userBase = sender
        userFortuneHistory.meiPiao = -gift.realPrice
        userFortuneHistory.reasonType = 0
        userFortuneHistory.reason = "ruiShow"
        userFortuneHistory.save flush: true
        //加美力值
        def userMeiLi = UserMeiLi.findByUserBase(sender)
        if (userMeiLi == null) {
            userMeiLi = new UserMeiLi()
            userMeiLi.userBase = sender
        }
        userMeiLi.qs += Math.min(MeiliConstant.POST_MAX_GIFT, 4 * gift.realPrice)
        userMeiLi.save flush: true
    }
}
