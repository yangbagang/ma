package com.ybg.yxym.ma

import grails.converters.JSON

class YueMeiCodeController {

    def yueMeiCodeService

    def create(String prefix, int begin, int end, int length) {
        yueMeiCodeService.createWithPrefix(prefix, begin, end, length)

        def map = [:]
        map.isSuccess = true
        map.message = ""
        map.errorCode = "0"
        map.data = "true"
        render map as JSON
    }

}
