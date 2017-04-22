package ma

import com.ybg.yxym.ma.objectMarshaller.UserBaseMarshaller
import com.ybg.yxym.ma.objectMarshaller.UserGiftMarshaller
import grails.converters.JSON

class BootStrap {

    def init = { servletContext ->
        JSON.registerObjectMarshaller(Date) {
            return it?.format("yyyy-MM-dd HH:mm:ss")
        }
        JSON.registerObjectMarshaller(new UserBaseMarshaller(), 9999)
        JSON.registerObjectMarshaller(new UserGiftMarshaller(), 9999)
    }
    def destroy = {
    }
}
