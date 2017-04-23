package com.ybg.yxym.ma

import com.ybg.yxym.utils.OrderUtil
import grails.transaction.Transactional

@Transactional
class OrderInfoService {

    def createOrder(UserBase userBase, RuiCard ruiCard) {
        def orderNo = OrderUtil.createOrderNo()
        //orderInfo
        def orderInfo = new OrderInfo()
        orderInfo.userBase = userBase
        orderInfo.orderNo = orderNo
        orderInfo.orderMoney = ruiCard.realPrice
        orderInfo.save flush: true

        orderInfo
    }

    def updateOrderStatus(OrderInfo orderInfo, TransactionInfo transactionInfo, String transaction_no) {
        orderInfo.transNo = transaction_no
        orderInfo.payStatus = 1 as Short
        orderInfo.confirmTime = new Date()
        orderInfo.payWay = Short.valueOf(transactionInfo.payType)
        transactionInfo.isSuccess = 1 as Short
        transactionInfo.updateTime = new Date()
        transactionInfo.save flush: true
        orderInfo.save flush: true
    }
}
