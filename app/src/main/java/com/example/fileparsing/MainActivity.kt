package com.example.fileparsing

import MainAdapter
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import androidx.core.text.HtmlCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private var doc: Document? = null
    private var fName: String? = ""
    private var fDownload: String? = ""
    private var fTime: String? = ""
    private var fLike: String? = ""
    private var fDislike: String? = ""
    private var fMD5: String? = ""
    private var fSHA1: String? = ""
    private var fLink: String? = ""
    private val files: MutableList<FileParsing> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startParsing(view: View) {

        fileNotFound.visibility = View.INVISIBLE
        files.clear()

        if (nameFile.text.toString() != ""){

            Thread(Runnable {
                try {
                    // Connect Jsoup
                    doc = Jsoup.connect("https://rghost.net/search/${nameFile.text}").header("Accept-Language", "ru").get()
                    // Get table
                    val table: Elements? = doc?.getElementsByClass("file-list__fileitem")
                    table?.forEach {
                        // Get files
                        val file: Element? = it
                        // Get content in file
                        val contentDescription: Element? = file?.getElementsByClass("fileset-card__content-descr")?.get(0)
                        // Get File name
                        val fileInfoName: Element? = contentDescription?.getElementsByClass("file-info__filename")?.get(0)
                        fName = fileInfoName?.getElementsByAttribute("title")?.get(0)?.text()
//                        // Get Icon
//                        var fileIcon = contentDescription?.getElementsByClass("file-info__icon")?.get(0)?.attributes().toString()
//                        fileIcon = fileIcon.substringBeforeLast(' ').substringAfterLast(' ')
                        // Get Count download
                        val infoDownload: Element? = contentDescription?.getElementsByClass("show-info-downloads")?.get(0)
                        fDownload = infoDownload?.text()
                        /// Get Time
                        val infoTime: Element? = contentDescription?.getElementsByClass("file-info__time")?.get(0)
                        fTime = infoTime?.getElementsByAttribute("title")?.get(0)?.text()
                        // Get Like/Dislike
                        val infoLikes: Element? = contentDescription?.getElementsByClass("show-info-likes")?.get(0)
                        fLike = infoLikes?.getElementsByClass("votes-like")?.get(0)?.text()
                        fDislike = infoLikes?.getElementsByClass("votes-dislike")?.get(0)?.text()
                        // Get MD5 and SHA1
                        fMD5 = contentDescription?.getElementsByClass("hash-info__content")?.getOrNull(0)?.text()
                        fSHA1 = contentDescription?.getElementsByClass("hash-info__content")?.getOrNull(1)?.text()
                        // Get limk
                        val infoLink: Element? = contentDescription?.getElementsByClass("btn-download")?.get(0)
                        fLink = infoLink?.attr("href")
                        // Add class in list
                        files.add(FileParsing(doc, fName, fDownload, fTime, fLike, fDislike, fMD5, fSHA1, fLink))
                    }
                    runOnUiThread {
                        if(files.size > 0) {
                            val adapter = MainAdapter(files, object : MainAdapter.Callback {
                                override fun onItemClicked(item: FileParsing) {

                                }
                            })
                            recycler.adapter = adapter
                        }
                        else {
                            fileNotFound.visibility = View.VISIBLE
                            fileNotFound.text = "К сожалению,поиск не дал никаких результатов"
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }).start()

        }
        else {
            fileNotFound.visibility = View.VISIBLE
            fileNotFound.text = "Введите название файла"
        }

    }

}
