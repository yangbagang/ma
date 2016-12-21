package com.ybg.yxym.ma

import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SiteCatalogController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond SiteCatalog.list(params), model:[siteCatalogCount: SiteCatalog.count()]
    }

    def list(String siteKey) {
        def map = [:]
        def site = SiteInfo.findBySiteKey(siteKey)
        if (site) {
            def catalogList = SiteCatalog.findAllBySite(site)

            map.isSuccess = true
            map.errorCode = "0"
            map.errorMsg = ""
            map.data = catalogList
        } else {
            map.isSuccess = false
            map.errorCode = "1"
            map.errorMsg = "不存在"
            map.data = ""
        }
        render map as JSON
    }

    def show(SiteCatalog siteCatalog) {
        respond siteCatalog
    }

    def create() {
        respond new SiteCatalog(params)
    }

    @Transactional
    def save(SiteCatalog siteCatalog) {
        if (siteCatalog == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (siteCatalog.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond siteCatalog.errors, view:'create'
            return
        }

        siteCatalog.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'siteCatalog.label', default: 'SiteCatalog'), siteCatalog.id])
                redirect siteCatalog
            }
            '*' { respond siteCatalog, [status: CREATED] }
        }
    }

    def edit(SiteCatalog siteCatalog) {
        respond siteCatalog
    }

    @Transactional
    def update(SiteCatalog siteCatalog) {
        if (siteCatalog == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (siteCatalog.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond siteCatalog.errors, view:'edit'
            return
        }

        siteCatalog.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'siteCatalog.label', default: 'SiteCatalog'), siteCatalog.id])
                redirect siteCatalog
            }
            '*'{ respond siteCatalog, [status: OK] }
        }
    }

    @Transactional
    def delete(SiteCatalog siteCatalog) {

        if (siteCatalog == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        siteCatalog.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'siteCatalog.label', default: 'SiteCatalog'), siteCatalog.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'siteCatalog.label', default: 'SiteCatalog'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def listTitle(Integer catalogId, Integer pageNum) {
        def map = [:]
        def catalog = SiteCatalog.get(catalogId)
        if (catalog) {
            //get address
            def pageUrl = ""
            if (pageNum == 1) {
                pageUrl = catalog.firstUrl
            } else {
                pageUrl = String.format(catalog.pagesUrl, pageNum)
            }

            //get content
            def url = catalog.site.siteUrl + pageUrl
            def html = url.toURL().text

            def titles = html.split("\n")
            def list = []
            titles.each { title ->
                //println title
                def matcher = title =~ catalog.titlePattern
                if (matcher.matches()) {
                    def vo = [:]
                    vo.href = matcher[0][1]
                    vo.title = matcher[0][2]
                    vo.type = catalog.catalog
                    vo.id = catalog.id
                    list.add(vo)
                }
            }

            map.isSuccess = true
            map.errorCode = "0"
            map.errorMsg = ""
            map.data = list
        } else {
            map.isSuccess = false
            map.errorCode = "1"
            map.errorMsg = "不存在"
            map.data = ""
        }
        render map as JSON
    }
}
