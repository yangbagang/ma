package com.ybg.yxym.ma

import grails.converters.JSON

class ShowZanController {

    /**
     * 显示全部赞
     * @param showId
     * @return
     */
    def list(Long showId) {
        def map = [:]
        def show = RuiShow.get(showId)
        if (show && show.flag == 1 as Short) {
            def zans = ShowZan.findAllByShow(show)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = zans
        } else {
            map.isSuccess = false
            map.message = "美秀不存在，请检查。"
            map.errorCode = "1"
            map.data = false
        }
        render map as JSON
    }
}
