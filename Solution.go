
package main

import (
    "container/list"
    "fmt"
    "strings"
)

// Index jump over 'http://'
const START_INDEX_HOSTNAME_PROPER = 7
const PATH_SEPARATOR = '/'

var targetHostname string

func crawl(startUrl string, htmlParser HtmlParser) []string {
    targetHostname = extractTargetHostname(startUrl)

    queue := list.List{}
    queue.PushBack(startUrl)

    visited := NewHashSet()
    visited.Add(startUrl)

    for queue.Len() > 0 {
        current := queue.Front().Value.(string)
        queue.Remove(queue.Front())

        neighbours := htmlParser.GetUrls(current)

        for _,next := range neighbours {
            if hasTargetHostname(next) && !visited.Contains(next) {
                visited.Add(next)
                queue.PushBack(next)
            }
        }
    }

    return visited.getKeys()
}

func extractTargetHostname(startUrl string) string {
    host := strings.Builder{}
    host.WriteString("http://")

    for i := START_INDEX_HOSTNAME_PROPER; i < len(startUrl); i++ {
        if startUrl[i] == PATH_SEPARATOR {
            break
        }
        host.WriteByte(startUrl[i])
    }
    return host.String()
}

func hasTargetHostname(url string) bool {
    if len(targetHostname) > len(url) {
        return false
    }

    for i := range targetHostname {
        if targetHostname[i] != url[i] {
            return false
        }
    }

    return len(targetHostname) == len(url) || url[len(targetHostname)] == PATH_SEPARATOR
}

type HashSet struct {
    conainer map[string]bool
}

func NewHashSet() HashSet {
    return HashSet{conainer: map[string]bool{}}
}

func (this *HashSet) Contains(url string) bool {
    return this.conainer[url]
}

func (this *HashSet) Add(url string) {
    this.conainer[url] = true
}

func (this *HashSet) Remove(url string) {
    delete(this.conainer, url)
}

func (this *HashSet) getKeys() []string {
    keys := make([]string, len(this.conainer))
    index:=0
    for key := range this.conainer {
        keys[index] = key
        index++
    }
    return keys
}

/*
Interface 'HtmlParser' is implemented internally at leetcode.com.
When running the code on the website, do not include this interface.
The comments below are from leetcode.com.
*/

// This is HtmlParser's API interface.
// You should not implement it, or speculate about its implementation
type HtmlParser struct {
     func GetUrls(url string) []string {}
}
