package com.ybg.yxym.ma

import grails.converters.JSON

class YueMeiCodeController {

    def yueMeiCodeService

    def create(String prefix, int begin, int end, int length) {
        yueMeiCodeService.createWithPrefix(prefix, begin, end, length)

        def result = [:]
        result.success = true
        result.message = ""
        render result as JSON
    }

}
