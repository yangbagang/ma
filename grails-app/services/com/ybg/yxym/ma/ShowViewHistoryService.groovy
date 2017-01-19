package com.ybg.yxym.ma

import grails.transaction.Transactional

@Transactional
class ShowViewHistoryService {

    def create(RuiShow ruiShow, UserBase userBase) {
        def history = ShowViewHistory.findByUserBaseAndShow(userBase, ruiShow)
        if (!history) {
            history.show = ruiShow
            history.userBase = userBase
            history.save flush: true
        }
    }

    def getViewNum(RuiShow ruiShow) {
        ShowViewHistory.countByShow(ruiShow)
    }
}
