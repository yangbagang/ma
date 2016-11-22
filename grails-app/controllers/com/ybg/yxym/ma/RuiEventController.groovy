package com.ybg.yxym.ma

import grails.converters.JSON

class RuiEventController {

    def ruiEventService

    /**
     * 显示全部话题
     * @param showId
     * @return
     */
    def list(Long showId) {
        def map = [:]
        def show = RuiShow.get(showId)
        if (show && show.flag == 1 as Short) {
            def events = RuiEvent.findAllByShow(show)*.event

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = events
        } else {
            map.isSuccess = false
            map.message = "美秀不存在，请检查。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 添加话题
     * @param showId
     * @param eventIds
     * @return
     */
    def appendEvent(Long showId, String eventIds) {
        def map = [:]
        def show = RuiShow.get(showId)
        if (show && show.flag == 1 as Short) {
            ruiEventService.appendEvents(show, eventIds)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = "true"
        } else {
            map.isSuccess = false
            map.message = "美秀不存在，请检查。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }
}
