package com.ybg.yxym.utils

import com.taobao.api.DefaultTaobaoClient
import com.taobao.api.TaobaoClient
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse

/**
 * Created by yangbagang on 2016/11/4.
 */
class SMSUtil {

    private static appKey = "23523013"
    private static appSecret = "5903a632797f97b2511c0c2c8fcfa986"
    private static captchaTemplate = "SMS_25340411"
    private static product_server = "http://gw.api.taobao.com/router/rest" //正式环境
    private static sand_box = "http://gw.api.tbsandbox.com/router/rest" //沙箱环境
    private static isDebug = false
    private static URL = isDebug ? sand_box : product_server

    /**
     * 发送验证码短信
     * @param mobile
     * @param msg
     */
    static sendCaptchaMsg(String mobile, String msg) {
        println("发送验证码：mobile=${mobile}, msg=${msg}")
        def param = "{\"captcha\":\"${msg}\"}"
        sendMsg(mobile, captchaTemplate, param)
    }

    static sendMsg(String mobile, String template,String param) {
        TaobaoClient client = new DefaultTaobaoClient(URL, appKey, appSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName("悦秀御美");
        req.setSmsParamString(param);
        req.setRecNum(mobile);
        req.setSmsTemplateCode(template);
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
        System.out.println(rsp.getBody());
    }

    static void main(String[] args) {
        sendCaptchaMsg("13926559879", "2345")
    }
}
