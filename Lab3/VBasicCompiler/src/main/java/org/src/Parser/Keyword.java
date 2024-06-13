package org.src.Parser;

/**
 * Keywords class
 */
class Keyword {
    String keyword; // string
    InsidesInterp keywordTok; // inside interpretation

    /**
     * Keywords constructor
     * @param str - keyword
     * @param t - kind of keyword
     */
    Keyword(String str, InsidesInterp t) {
        keyword = str;
        keywordTok = t;
    }
}
