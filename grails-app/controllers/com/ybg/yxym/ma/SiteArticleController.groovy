package com.ybg.yxym.ma

import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SiteArticleController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond SiteArticle.list(params), model:[siteArticleCount: SiteArticle.count()]
    }

    def show(SiteArticle siteArticle) {
        respond siteArticle
    }

    def create() {
        respond new SiteArticle(params)
    }

    @Transactional
    def save(SiteArticle siteArticle) {
        if (siteArticle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (siteArticle.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond siteArticle.errors, view:'create'
            return
        }

        siteArticle.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'siteArticle.label', default: 'SiteArticle'), siteArticle.id])
                redirect siteArticle
            }
            '*' { respond siteArticle, [status: CREATED] }
        }
    }

    def edit(SiteArticle siteArticle) {
        respond siteArticle
    }

    @Transactional
    def update(SiteArticle siteArticle) {
        if (siteArticle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (siteArticle.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond siteArticle.errors, view:'edit'
            return
        }

        siteArticle.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'siteArticle.label', default: 'SiteArticle'), siteArticle.id])
                redirect siteArticle
            }
            '*'{ respond siteArticle, [status: OK] }
        }
    }

    @Transactional
    def delete(SiteArticle siteArticle) {

        if (siteArticle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        siteArticle.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'siteArticle.label', default: 'SiteArticle'), siteArticle.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'siteArticle.label', default: 'SiteArticle'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def viewArticle(Integer catalogId, String articleUrl) {
        def map = [:]
        def catalog = SiteCatalog.get(catalogId)
        def article = SiteArticle.findByCatalog(catalog)
        if (catalog && article) {
            //get content
            def url = articleUrl.startsWith("/") ? catalog.site.siteUrl + articleUrl : articleUrl
            def html = url.toURL().text

            //parse
            def titles = html.split("\n")
            def list = []
            titles.each { title ->
                //println title
                def matcher = title =~ /.*source src="(.+)" type="video\/mp4" label="360p" res="360" .*/
                if (matcher.matches()) {
                    def vo = [:]
                    vo.href = matcher[0][1]
                    vo.type = article.type
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
