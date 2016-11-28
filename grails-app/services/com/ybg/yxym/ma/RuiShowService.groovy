package com.ybg.yxym.ma

import com.ybg.yxym.utils.MeiliConstant
import grails.transaction.Transactional

@Transactional
class RuiShowService {

    def ping(RuiShow ruiShow, UserBase userBase, String content) {
        //生成评论实例
        def showPing = new ShowPing()
        showPing.show = ruiShow
        showPing.createTime = new Date()
        showPing.userBase = userBase
        showPing.ping = content
        showPing.save flush: true
        //计算积分，评论者获得活力值
        def postMeiLi = getUserMeili(userBase)
        postMeiLi.hl = postMeiLi.hl + MeiliConstant.POST_PING
        postMeiLi.save flush: true

        MeiLiHistory.createInstance(userBase, MeiliConstant.MEILI_HL, MeiliConstant.POST_PING, "评论", ruiShow.id)
        //计算积分，被评论者获得人气值
        def getMeiLi = getUserMeili(ruiShow.userBase)
        getMeiLi.rq = getMeiLi.rq + MeiliConstant.GET_PING
        getMeiLi.save flush: true

        MeiLiHistory.createInstance(ruiShow.userBase, MeiliConstant.MEILI_RQ, MeiliConstant.GET_PING, "评论", ruiShow.id)

        return showPing
    }

    def zan(RuiShow ruiShow, UserBase userBase) {
        //生成赞实例
        def showZan = new ShowZan()
        showZan.show = ruiShow
        showZan.createTime = new Date()
        showZan.userBase = userBase
        showZan.save flush: true
        //计算积分，赞者获得亲善值
        def postMeiLi = getUserMeili(userBase)
        postMeiLi.qs = postMeiLi.qs + MeiliConstant.POST_ZAN
        postMeiLi.save flush: true

        MeiLiHistory.createInstance(userBase, MeiliConstant.MEILI_QS, MeiliConstant.POST_ZAN, "赞", ruiShow.id)
        //计算积分，被评论者获得人气值
        def getMeiLi = getUserMeili(ruiShow.userBase)
        getMeiLi.rq = getMeiLi.rq + MeiliConstant.GET_PING
        getMeiLi.save flush: true

        MeiLiHistory.createInstance(ruiShow.userBase, MeiliConstant.MEILI_RQ, MeiliConstant.GET_ZAN, "赞", ruiShow.id)

        return showZan
    }

    def share(RuiShow ruiShow, UserBase userBase) {
        //生成分享实例
        def showShare = new ShowShare()
        showShare.show = ruiShow
        showShare.createTime = new Date()
        showShare.userBase = userBase
        showShare.save flush: true
        //计算积分，分享者获得活力值
        def postMeiLi = getUserMeili(userBase)
        postMeiLi.hl = postMeiLi.hl + MeiliConstant.POST_SHARE
        postMeiLi.save flush: true

        MeiLiHistory.createInstance(userBase, MeiliConstant.MEILI_HL, MeiliConstant.POST_SHARE, "分享", ruiShow.id)
        //计算积分，被评论者获得人气值
        def getMeiLi = getUserMeili(ruiShow.userBase)
        getMeiLi.rq = getMeiLi.rq + MeiliConstant.GET_SHARE
        getMeiLi.save flush: true

        MeiLiHistory.createInstance(ruiShow.userBase, MeiliConstant.MEILI_RQ, MeiliConstant.GET_SHARE, "分享", ruiShow.id)

        return showShare
    }

    def getUserMeili(UserBase userBase) {
        def meili = UserMeiLi.findByUserBase(userBase)
        if (!meili) {
            meili = new UserMeiLi()
            meili.userBase = userBase
            meili.save flush: true
        }
        meili
    }

    def create(UserBase userBase, RuiBar ruiBar, String thumbnail, String title, Short type) {
        //生成实例
        def show = new RuiShow()
        show.userBase = userBase
        show.ruiBar = ruiBar
        show.thumbnail = thumbnail
        show.title = title
        show.type = type
        show.save flush: true

        //计算活力值
        def postMeiLi = getUserMeili(userBase)
        postMeiLi.hl = postMeiLi.hl + MeiliConstant.POST_PHOTO_VIEW
        postMeiLi.save flush: true

        MeiLiHistory.createInstance(userBase, MeiliConstant.MEILI_HL, MeiliConstant.POST_PHOTO_VIEW, "发布", show.id)
        show
    }
}
