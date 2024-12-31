
// const {Queue} = require('@datastructures-js/queue');
/*
 Queue is internally included in the solution file on leetcode.
 When running the code on leetcode it should stay commented out. 
 It is mentioned here just for information about the external library 
 that is applied for this data structure.
 */

/**
 * @param {string} startUrl
 * @param {HtmlParser} htmlParser
 * @return {string[]}
 */
var crawl = function (startUrl, htmlParser) {

    // Index jump over 'http://'
    this.START_INDEX_HOSTNAME_PROPER = 7;
    this.PATH_SEPARATOR = '/';
    this.targetHostname = extractTargetHostname(startUrl);

    const queue = new Queue();
    queue.enqueue(startUrl);

    const visited = new Set();
    visited.add(startUrl);

    while (!queue.isEmpty()) {
        const current = queue.dequeue();
        const neighbours = htmlParser.getUrls(current);

        for (let next of neighbours) {
            if (hasTargetHostname(next) && !visited.has(next)) {
                visited.add(next);
                queue.enqueue(next);
            }
        }
    }

    return Array.from(visited);
};

/**
 * @param {string} startUrl
 * @return {string}
 */
function extractTargetHostname(startUrl) {
    const host = "http://".split('');
    for (let i = this.START_INDEX_HOSTNAME_PROPER; i < startUrl.length; ++i) {
        if (startUrl.charAt(i) === this.PATH_SEPARATOR) {
            break;
        }
        host.push(startUrl.charAt(i));
    }
    return host.join('');
}

/**
 * @param {string} url
 * @return {boolean}
 */
function hasTargetHostname(url) {
    if (this.targetHostname.length > url.length) {
        return false;
    }
    for (let i = 0; i < this.targetHostname.length; ++i) {
        if (this.targetHostname.charAt(i) !== url.charAt(i)) {
            return false;
        }
    }
    return this.targetHostname.length === url.length || url.charAt(this.targetHostname.length) === this.PATH_SEPARATOR;
}

/*
 Interface 'HtmlParser' is implemented internally at leetcode.com.
 When running the code on the website, do not include this interface.
 The comments below are from leetcode.com.
 */

// This is the HtmlParser's API interface.
// You should not implement it, or speculate about its implementation
function HtmlParser() {
    /**
     * @param {string} url
     * @return {string[]}
     */
    this.getUrls = function (url) { };
}
