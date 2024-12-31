
import java.util.LinkedList

class Solution {

    private companion object {
        // Index jump over 'http://'
        const val START_INDEX_HOSTNAME_PROPER = 7
        const val PATH_SEPARATOR = '/'
    }

    private lateinit var targetHostname: String

    fun crawl(startUrl: String, htmlParser: HtmlParser): List<String> {
        targetHostname = extractTargetHostname(startUrl)

        val queue = LinkedList<String>()
        queue.add(startUrl)

        val visited = HashSet<String>()
        visited.add(startUrl)

        while (!queue.isEmpty()) {
            val current = queue.poll()
            val neighbours = htmlParser.getUrls(current)

            for (next in neighbours) {
                if (hasTargetHostname(next) && visited.add(next)) {
                    queue.add(next)
                }
            }
        }

        return ArrayList<String>(visited)
    }

    private fun extractTargetHostname(startUrl: String): String {
        val host = StringBuilder("http://")
        for (i in START_INDEX_HOSTNAME_PROPER..<startUrl.length) {
            if (startUrl[i] == PATH_SEPARATOR) {
                break
            }
            host.append(startUrl[i])
        }
        return host.toString()
    }

    private fun hasTargetHostname(url: String): Boolean {
        if (targetHostname.length > url.length) {
            return false
        }

        for (i in targetHostname.indices) {
            if (targetHostname[i] != url[i]) {
                return false
            }
        }
        return targetHostname.length == url.length || url[targetHostname.length] == PATH_SEPARATOR
    }
}

/*
Interface 'HtmlParser' is implemented internally at leetcode.com.
When running the code on the website, do not include this interface.
The comments below are from leetcode.com.
 */

// This is the HtmlParser's API interface.
// You should not implement it, or speculate about its implementation
class HtmlParser {
    fun getUrls(url: String): List<String> {}
}
