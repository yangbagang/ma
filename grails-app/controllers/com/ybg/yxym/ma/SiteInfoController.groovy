package com.ybg.yxym.ma

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SiteInfoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond SiteInfo.list(params), model:[siteInfoCount: SiteInfo.count()]
    }

    def show(SiteInfo siteInfo) {
        respond siteInfo
    }

    def create() {
        respond new SiteInfo(params)
    }

    @Transactional
    def save(SiteInfo siteInfo) {
        if (siteInfo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (siteInfo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond siteInfo.errors, view:'create'
            return
        }

        siteInfo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'siteInfo.label', default: 'SiteInfo'), siteInfo.id])
                redirect siteInfo
            }
            '*' { respond siteInfo, [status: CREATED] }
        }
    }

    def edit(SiteInfo siteInfo) {
        respond siteInfo
    }

    @Transactional
    def update(SiteInfo siteInfo) {
        if (siteInfo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (siteInfo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond siteInfo.errors, view:'edit'
            return
        }

        siteInfo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'siteInfo.label', default: 'SiteInfo'), siteInfo.id])
                redirect siteInfo
            }
            '*'{ respond siteInfo, [status: OK] }
        }
    }

    @Transactional
    def delete(SiteInfo siteInfo) {

        if (siteInfo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        siteInfo.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'siteInfo.label', default: 'SiteInfo'), siteInfo.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'siteInfo.label', default: 'SiteInfo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
