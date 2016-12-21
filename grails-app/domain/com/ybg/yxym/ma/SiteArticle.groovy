package com.ybg.yxym.ma

class SiteArticle {

    static belongsTo = [catalog: SiteCatalog]

    static constraints = {
    }

    String title
    Integer type
    String contentPattern
}
