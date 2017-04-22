package com.ybg.yxym.ma.objectMarshaller

import com.ybg.yxym.ma.UserGift
import com.ybg.yxym.utils.DateUtil
import grails.converters.JSON
import org.grails.web.converters.exceptions.ConverterException
import org.grails.web.converters.marshaller.ObjectMarshaller
import org.grails.web.json.JSONWriter

/**
 * Created by yangbagang on 2017/4/22.
 */
class UserGiftMarshaller implements ObjectMarshaller<JSON> {

    @Override
    boolean supports(Object object) {
        return object instanceof UserGift
    }

    @Override
    void marshalObject(Object object, JSON converter) throws ConverterException {
        JSONWriter writer = converter.getWriter()
        writer.object()
        writer.key('id').value(object.id)
                .key("fromUserId").value(object.fromUser.id)
                .key('fromUserName').value(object.fromUser.nickName)
                .key('fromUserAvatar').value(object.fromUser.avatar)
                .key("toUserId").value(object.targetUser.id)
                .key('toUserName').value(object.targetUser.nickName)
                .key('toUserAvatar').value(object.targetUser.avatar)
                .key("giftName").value(object.gift.name)
                .key('giftPic').value(object.gift.image)
                .key('giftPrice').value(object.gift.realPrice)
                .key('createTime').value(DateUtil.getTimeString(object.createTime))
        writer.endObject()
    }

}
