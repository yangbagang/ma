package com.ybg.yxym.ma

import grails.transaction.Transactional

@Transactional
class UserLabelService {

    def list(UserBase userBase) {
        def userLabelList = UserLabel.findAllByUserBase(userBase)*.systemLabel
        def allLabelList = SystemLabel.list()
        def data = []
        for (SystemLabel label : allLabelList) {
            def map = [:]
            map.label = label.labelName
            map.catalog = label.catalog
            if (userLabelList != null && userLabelList.contains(label)) {
                map.flag = 1
            } else {
                map.flag = 0
            }
            data.add(map)
        }
        data
    }

    def update(UserBase userBase, String[] names) {
        def userLabelList = UserLabel.findAllByUserBase(userBase)
        //删除
        userLabelList.each {
            it.delete flush: true
        }
        //新增
        for (String name : names) {
            def userLabel = new UserLabel()
            userLabel.systemLabel = getSystemLabel(name)
            userLabel.userBase = userBase
            userLabel.save flush: true
        }
    }

    static getSystemLabel(String name) {
        def systemLabel = SystemLabel.findByLabelName(name)
        if (systemLabel) {
            return systemLabel
        }
        systemLabel = new SystemLabel()
        systemLabel.labelName = name
        systemLabel.catalog = name
        systemLabel.save flush: true
        return systemLabel
    }
}
