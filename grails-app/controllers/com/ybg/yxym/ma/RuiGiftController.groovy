package com.ybg.yxym.ma

import grails.converters.JSON

class RuiGiftController {

    /**
     * 列出礼物。带分页。
     * @param pageNum 显示第几页
     * @param pageSize 每页显示多少条
     * @return
     */
    def list(Integer pageNum, Integer pageSize) {
        def map = [:]
        if (pageNum != null && pageSize != null) {
            def c = RuiGift.createCriteria()
            def result = c.list(max: pageSize, offset: (pageNum - 1) * pageSize) {
                eq("flag", 1 as Short)
                order("realPrice", "asc")
            }
            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = result
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = ""
        }
        render map as JSON
    }

}
