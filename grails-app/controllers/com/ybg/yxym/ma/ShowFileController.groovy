package com.ybg.yxym.ma

import grails.converters.JSON

class ShowFileController {

    def showFileService

    /**
     * 显示全部附件
     * @param showId
     * @return
     */
    def list(Long showId) {
        def map = [:]
        def show = RuiShow.get(showId)
        if (show && show.flag == 1 as Short) {
            def files = ShowFile.findAllByShowAndFlag(show, 1 as Short)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = files
        } else {
            map.isSuccess = false
            map.message = "美秀不存在，请检查。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 为某个美秀增加图片或视频等文件。
     * @param showId
     * @param fileIds
     * @param type
     */
    def addFiles(Long showId, String fileIds, Short type) {
        def map = [:]
        def show = RuiShow.get(showId)
        if (show && show.flag == 1 as Short) {
            showFileService.appendFiles(show, fileIds, type)

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
