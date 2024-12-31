
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Solution {

    // Index jump over 'http://'
    private static final int START_INDEX_HOSTNAME_PROPER = 7;
    private static final char PATH_SEPARATOR = '/';
    private String targetHostname;

    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        targetHostname = extractTargetHostname(startUrl);

        Queue<String> queue = new LinkedList<>();
        queue.add(startUrl);

        Set<String> visited = new HashSet<>();
        visited.add(startUrl);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            List<String> neighbours = htmlParser.getUrls(current);

            for (String next : neighbours) {
                if (hasTargetHostname(next) && visited.add(next)) {
                    queue.add(next);
                }
            }
        }

        return new ArrayList<>(visited);
    }

    private String extractTargetHostname(String startUrl) {
        StringBuilder host = new StringBuilder("http://");
        for (int i = START_INDEX_HOSTNAME_PROPER; i < startUrl.length(); ++i) {
            if (startUrl.charAt(i) == PATH_SEPARATOR) {
                break;
            }
            host.append(startUrl.charAt(i));
        }
        return host.toString();
    }

    private boolean hasTargetHostname(String url) {
        if (targetHostname.length() > url.length()) {
            return false;
        }

        for (int i = 0; i < targetHostname.length(); ++i) {
            if (targetHostname.charAt(i) != url.charAt(i)) {
                return false;
            }
        }
        return targetHostname.length() == url.length() || url.charAt(targetHostname.length()) == PATH_SEPARATOR;
    }
}

/*
Interface 'HtmlParser' is implemented internally at leetcode.com.
When running the code on the website, do not include this interface.
The comments below are from leetcode.com.
 */

// This is the HtmlParser's API interface.
// You should not implement it, or speculate about its implementation
interface HtmlParser {

    public List<String> getUrls(String url);// {}
}
