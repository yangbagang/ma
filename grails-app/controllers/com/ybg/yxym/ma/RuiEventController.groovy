package com.ybg.yxym.ma

import grails.converters.JSON

class RuiEventController {

    /**
     * 显示所有开放中主题
     * @return
     */
    def list() {
        def events = RuiEvent.findAllByFlag(1 as Short)
        def map = [:]
        map.isSuccess = true
        map.message = "登录完成"
        map.errorCode = "0"
        map.data = events
        render map as JSON
    }
}
