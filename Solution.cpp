
#include <queue>
#include <string>
#include <vector>
#include <string_view>
#include <unordered_set>
using namespace std;

/*
Interface 'HtmlParser' is implemented internally at leetcode.com.
When running the code on the website, do not include this interface.
The comments below are from leetcode.com.
*/

// This is the HtmlParser's API interface.
// You should not implement it, or speculate about its implementation
class HtmlParser {
public:
    vector<string> getUrls(string url);
};

class Solution {

    // Index jump over 'http://'
    static const int START_INDEX_HOSTNAME_PROPER = 7;
    static const char PATH_SEPARATOR = '/';
    string targetHostname;

public:
    vector<string> crawl(string startUrl, HtmlParser htmlParser) {
        targetHostname = extractTargetHostname(startUrl);

        queue<string> queue;
        queue.push(startUrl);

        unordered_set<string> visited;
        visited.insert(startUrl);

        while (!queue.empty()) {
            string current = queue.front();
            queue.pop();

            vector<string> neighbours = htmlParser.getUrls(current);

            for (const auto& next : neighbours) {
                if (hasTargetHostname(next) && !visited.contains(next)) {
                    visited.insert(next);
                    queue.push(next);
                }
            }
        }

        return vector<string>(visited.begin(), visited.end());
    }

private:
    string extractTargetHostname(string_view startUrl) const {
        string host("http://");
        for (size_t i = START_INDEX_HOSTNAME_PROPER; i < startUrl.length(); ++i) {
            if (startUrl[i] == PATH_SEPARATOR) {
                break;
            }
            host.push_back(startUrl[i]);
        }
        return host;
    }

    bool hasTargetHostname(string_view url) const {
        if (targetHostname.length() > url.length()) {
            return false;
        }
        for (size_t i = 0; i < targetHostname.length(); ++i) {
            if (targetHostname[i] != url[i]) {
                return false;
            }
        }
        return targetHostname.length() == url.length() || url[targetHostname.length()] == PATH_SEPARATOR;
    }
};
