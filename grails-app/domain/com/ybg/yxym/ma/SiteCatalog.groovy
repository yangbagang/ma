package com.ybg.yxym.ma

class SiteCatalog {

    static belongsTo = [site: SiteInfo]

    static constraints = {
    }

    String catalogName
    String catalogKey
    String firstUrl
    String pagesUrl
    String titlePattern
    Integer catalog
}
