
using System;


class Solution
{
    // Index jump over 'http://'
    private static readonly int START_INDEX_HOSTNAME_PROPER = 7;
    private static readonly char PATH_SEPARATOR = '/';
    private string? targetHostname;

    public IList<string> Crawl(string startUrl, HtmlParser htmlParser)
    {
        targetHostname = ExtractTargetHostname(startUrl);

        Queue<string> queue = new Queue<string>();
        queue.Enqueue(startUrl);

        HashSet<string> visited = new HashSet<string>();
        visited.Add(startUrl);

        while (queue.Count > 0)
        {
            string current = queue.Dequeue();
            IList<string> neighbours = htmlParser.GetUrls(current);

            foreach (string next in neighbours)
            {
                if (HasTargetHostname(next) && visited.Add(next))
                {
                    queue.Enqueue(next);

                }
            }
        }

        return new List<string>(visited);
    }

    private string ExtractTargetHostname(string startUrl)
    {
        StringBuilder host = new StringBuilder("http://");
        for (int i = START_INDEX_HOSTNAME_PROPER; i < startUrl.Length; ++i)
        {
            if (startUrl[i] == PATH_SEPARATOR)
            {
                break;
            }
            host.Append(startUrl[i]);
        }
        return host.ToString();
    }

    private bool HasTargetHostname(string url)
    {
        if (targetHostname!.Length > url.Length)
        {
            return false;
        }

        for (int i = 0; i < targetHostname.Length; ++i)
        {
            if (targetHostname[i] != url[i])
            {
                return false;
            }
        }
        return targetHostname.Length == url.Length || url[targetHostname.Length] == PATH_SEPARATOR;
    }
}

/*
Interface 'HtmlParser' is implemented internally at leetcode.com.
When running the code on the website, do not include this interface.
The comments below are from leetcode.com.
 */

// This is the HtmlParser's API interface.
// You should not implement it, or speculate about its implementation
class HtmlParser
{
    public List<String> GetUrls(String url) { }
}
