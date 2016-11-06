package com.ybg.yxym.ma

import grails.converters.JSON

class RuiBarController {

    /**
     * 显示可用板块（吧名）
     * @return
     */
    def list() {
        def bars = RuiBar.findAllByFlag(1 as Short)
        def map = [:]
        map.isSuccess = true
        map.message = "登录完成"
        map.errorCode = "0"
        map.data = bars
        render map as JSON
    }
}
