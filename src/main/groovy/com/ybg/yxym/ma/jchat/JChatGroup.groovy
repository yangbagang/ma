package com.ybg.yxym.ma.jchat

import cn.jiguang.common.resp.APIConnectionException
import cn.jiguang.common.resp.APIRequestException
import org.slf4j.LoggerFactory

/**
 * Created by yangbagang on 2017/4/20.
 */
class JChatGroup extends JChatBase {

    private static LOG = LoggerFactory.getLogger(JChatGroup.class)

    static create(String owner, String groupName) {
        def groupId = 0L
        try {
            def res = client.createGroup(owner, groupName, groupName, owner)
            groupId = res.gid
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e)
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e)
            LOG.info("HTTP Status: " + e.getStatus())
            LOG.info("Error Message: " + e.getMessage())
        }
        groupId
    }

    static remove(Long groupId) {
        try {
            client.deleteGroup(groupId)
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e)
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e)
            LOG.info("HTTP Status: " + e.getStatus())
            LOG.info("Error Message: " + e.getMessage())
        }
    }

    static addMembers(Long groupId, String[] members) {
        try {
            client.addOrRemoveMembers(groupId, members, null)
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e)
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e)
            LOG.info("HTTP Status: " + e.getStatus())
            LOG.info("Error Message: " + e.getMessage())
        }
    }

    static delMembers(Long groupId, String[] members) {
        try {
            client.addOrRemoveMembers(groupId, null, members );
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

}
