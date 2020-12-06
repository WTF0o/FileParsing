package com.example.fileparsing

import org.jsoup.nodes.Document

class FileParsing {

    constructor(doc: Document?, fName: String?, fDownload: String?, fTime: String?, fLike: String?, fDislike: String?, fMD5: String?, fSHA1: String?, fLink: String?) {
        this.doc = doc
        this.fName = fName
        this.fDownload = fDownload
        this.fTime = fTime
        this.fLike = fLike
        this.fDislike = fDislike
        this.fMD5 = fMD5
        this.fSHA1 = fSHA1
        this.fLink = fLink
    }

     var doc: Document? = null
     var fName: String? = ""
     var fDownload: String? = ""
     var fTime: String? = ""
     var fLike: String? = ""
     var fDislike: String? = ""
     var fMD5: String? = ""
     var fSHA1: String? = ""
     var fLink: String? = ""

}