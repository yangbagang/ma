package com.ybg.yxym.utils

import com.qiniu.pili.*
import grails.converters.JSON

/**
 * Created by yangbagang on 2017/1/16.
 */
class QiNiuUtil {

    static accessKey = "Qk5HOCRkT3g6oSUkycE18-DpuNR1DkuZ3GfArQRb"
    static secretKey = "r7foT2E9U0CdBeKuVd4tqVu9OA3nlnnuf-MVHAND"
    static hubName = "yuemei2017"
    static streamKeyPrefix ="live"

    static client = new Client(accessKey, secretKey)
    static hub = client.newHub(hubName)

    static {
        Config.APIHost = "pili.qiniuapi.com"
    }

    static createStream(String key) {
        //创建流
        try {
            return hub.create(streamKeyPrefix + key)
        } catch (PiliException e) {
            println e.message
            e.printStackTrace()
            return null
        }
    }

    static getStream(String key) {
        //获得流
        Stream stream = null
        try {
            stream = hub.get(streamKeyPrefix + key)
        } catch (PiliException e) {
            println e.message
            e.printStackTrace()
        }
        return stream
    }

    static listAllStream() {
        //列出所有流
        Hub.ListRet listRet = null
        try {
            listRet = hub.list(streamKeyPrefix, 0, "")
        } catch (PiliException e) {
            e.printStackTrace()
        }
        return listRet
    }

    static listLiveStream() {
        //列出正在直播的流
        Hub.ListRet listRet = null
        try {
            listRet = hub.listLive(streamKeyPrefix, 0, "")
        } catch (PiliException e) {
            e.printStackTrace()
        }
        return listRet
    }

    static disableStream(Stream stream, String key) {
        //禁用流
        try {
            stream.disable()
            return hub.get(streamKeyPrefix + key)
        } catch (PiliException e) {
            e.printStackTrace()
            return null
        }
    }

    static enableStream(Stream stream, String key) {
        //启用流
        try {
            stream.enable()
            return hub.get(streamKeyPrefix + key)
        } catch (PiliException e) {
            e.printStackTrace()
            return null
        }
    }

    static getRtmpPublishUrl(String key) {
        // RTMP推流地址
        return client.RTMPPublishURL("pili-publish.5yxym.com", hubName, streamKeyPrefix + key, 3600)
    }

    static getRtmpLiveUrl(String key) {
        //RTMP直播地址
        return client.RTMPPlayURL("pili-live-rtmp.5yxym.com", hubName, streamKeyPrefix + key)
    }

    static getPublishJSON(String key, Stream stream) {
        def map = [:]
        map.id = streamKeyPrefix + key
        map.title = key
        map.hub = hubName
        map.publishKey = stream.key
        map.publishSecurity = "static"
        def host = [:]
        def publish = [:]
        publish.rtmp = ""
        host.publish = publish
        map.host = host
        map as JSON
    }

    static void main(String[] args) {

    }

}
