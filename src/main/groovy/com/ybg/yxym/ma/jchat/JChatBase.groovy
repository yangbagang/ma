package com.ybg.yxym.ma.jchat

import cn.jmessage.api.JMessageClient

/**
 * Created by yangbagang on 2017/4/20.
 */
class JChatBase {

    private static appkey = "4c258e397c2605ddce2814a4"
    private static masterSecret = "31904e1a71f962274dd2bd99"
    protected static client = new JMessageClient(appkey, masterSecret)

}
