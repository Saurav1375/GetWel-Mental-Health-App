package com.example.getwell.screens.resourcesection.education

import android.text.Html
import android.text.Spanned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.net.URL

data class FeedItem(
    var title: String = "",
    var description: String = "", // summary in Atom, description in RSS
    var pubDate: String = "",    // published in Atom, pubDate in RSS
    var link: String = ""        // href in Atom, link in RSS
)



class FeedRepository {
    suspend fun getMultipleFeedItems(feedUrls: List<String>): Map<String, Result<List<FeedItem>>> {
        return feedUrls.associateWith { url ->
            runCatching {
                withContext(Dispatchers.IO) {
                    parseFeed(url)
                }
            }
        }
    }

    private fun parseFeed(feedUrl: String): List<FeedItem> {
        val items = mutableListOf<FeedItem>()
        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = false
        val parser = factory.newPullParser()

        try {
            val url = URL(feedUrl)
            url.openStream().use { inputStream ->
                parser.setInput(inputStream, null)
                var eventType = parser.eventType
                var currentItem: FeedItem? = null
                var currentText = StringBuilder()
                var isAtomFeed = false

                // Determine feed type
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        when (parser.name) {
                            "feed" -> isAtomFeed = true
                            "rss" -> isAtomFeed = false
                        }
                        break
                    }
                    eventType = parser.next()
                }

                // Reset parser
                url.openStream().use { newInputStream ->
                    parser.setInput(newInputStream, null)
                    eventType = parser.eventType

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        when (eventType) {
                            XmlPullParser.START_TAG -> {
                                when (parser.name) {
                                    "item", "entry" -> {
                                        currentItem = FeedItem()
                                        currentText = StringBuilder()
                                    }
                                    "title", "description", "summary", "published", "pubDate", "link" -> {
                                        currentText = StringBuilder()
                                    }
                                    "link" -> {
                                        if (isAtomFeed) {
                                            val link = parser.getAttributeValue(null, "href")
                                            if (link != null) {
                                                currentItem?.link = link
                                            }
                                            else{
                                                currentItem?.link = ""
                                            }
                                        }
                                    }
                                }
                            }
                            XmlPullParser.TEXT -> currentText.append(parser.text)
                            XmlPullParser.END_TAG -> {
                                when (parser.name) {
                                    "item", "entry" -> currentItem?.let { items.add(it) }
                                    "title" -> currentItem?.title = cleanupHtml(currentText.toString())
                                    "description", "summary" -> currentItem?.description = cleanupHtml(currentText.toString())
                                    "pubDate", "published" -> currentItem?.pubDate = formatDate(currentText.toString().trim())
                                    "link" -> {
                                        if (!isAtomFeed && currentText.isNotBlank()) {
                                            currentItem?.link = currentText.toString().trim()
                                        }
                                    }
                                }
                            }
                        }
                        eventType = parser.next()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return items
    }

    private fun cleanupHtml(html: String): String {
        return try {
            // First, handle CDATA sections
            val withoutCdata = html.replace("<![CDATA[", "").replace("]]>", "")

            // Convert HTML to plain text
            val spanned: Spanned = Html.fromHtml(withoutCdata, Html.FROM_HTML_MODE_LEGACY)

            // Convert to string and clean up extra whitespace
            spanned.toString().trim()
                .replace("\\s+".toRegex(), " ") // Replace multiple spaces with single space
                .replace("\n+".toRegex(), "\n") // Replace multiple newlines with single newline
        } catch (e: Exception) {
            // If anything goes wrong, return the original string with basic cleanup
            html.replace("<![CDATA[", "")
                .replace("]]>", "")
                .replace(Regex("<[^>]*>"), "") // Remove HTML tags
                .trim()
        }
    }

    private fun formatDate(dateString: String): String {
        // You can add date formatting logic here if needed
        return dateString.trim()
    }
}
class FeedException(message: String) : Exception(message)